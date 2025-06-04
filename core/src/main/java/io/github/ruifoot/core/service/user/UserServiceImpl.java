package io.github.ruifoot.core.service.user;


import io.github.ruifoot.domain.model.user.Users;
import io.github.ruifoot.domain.repository.UserRepository;
import io.github.ruifoot.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String getUsername(long id) {
        Users users = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return users.getUsername();
    }

    @Override
    public String getUsername(String email) {
        Users users = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return users.getUsername();
    }
    // UserServiceImpl 클래스는 UserService 인터페이스를 구현합니다.
    // 이 클래스는 사용자 관련 비즈니스 로직을 처리하는 서비스 계층의 구현체입니다.
    // 현재는 빈 구현체로, 추후에 필요한 메서드를 추가하여 기능을 확장할 수 있습니다.


}
