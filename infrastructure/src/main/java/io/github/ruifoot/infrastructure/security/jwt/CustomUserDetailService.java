package io.github.ruifoot.infrastructure.security.jwt;

import io.github.ruifoot.domain.model.user.Users;
import io.github.ruifoot.domain.repository.UserRepository;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return createUserDetails(userRepository.findByEmail(email));
    }

    private UserDetails createUserDetails(Optional<Users> users) {
        return userMapper.toEntity(users.orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
