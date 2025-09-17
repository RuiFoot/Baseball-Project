package io.github.ruifoot.core.service.auth;

import io.github.ruifoot.common.exception.CustomException;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.domain.dto.auth.request.AdminApprovalDto;
import io.github.ruifoot.domain.dto.auth.request.AdminRegisterDto;
import io.github.ruifoot.domain.dto.auth.request.RegisterDto;
import io.github.ruifoot.domain.model.auth.JwtToken;
import io.github.ruifoot.domain.model.user.UserBaseball;
import io.github.ruifoot.domain.model.user.UserPositions;
import io.github.ruifoot.domain.model.user.UserProfiles;
import io.github.ruifoot.domain.model.user.Users;
import io.github.ruifoot.domain.repository.UserRepository;
import io.github.ruifoot.domain.service.auth.AuthService;
import io.github.ruifoot.infrastructure.cache.redis.RedisService;
import io.github.ruifoot.infrastructure.security.jwt.JwtService;
import io.github.ruifoot.infrastructure.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String getUsername(long id) {
        return userRepository.findById(id)
                .map(Users::getUsername)
                .orElse(String.valueOf(id));
    }

    private Users registerBasic(String username, String email, String password) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setAdminApproved(false);

        // Save user
        return user;
    }

    @Override
    public Users register(RegisterDto registerDto) {
        // Extract basic user information
        String username = registerDto.username();
        String email = registerDto.email();
        String password = registerDto.password();

        // Check if username or email already exists
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setAdminApproved(false);

        // Create UserProfiles if profile data is provided
        UserProfiles userProfile = null;
        if (registerDto.profile() != null) {
            userProfile = new UserProfiles();
            userProfile.setUserId(0); // Will be set after user is saved

            // Extract profile data
            userProfile.setFullName(registerDto.profile().fullName());

            // Convert string date to SQL Date if provided
            String birthDateStr = registerDto.profile().birthDate();
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                try {
                    LocalDate localDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_DATE);
                    userProfile.setBirthDate(Date.valueOf(localDate));
                } catch (Exception e) {
                    // Handle date parsing error
                    System.err.println("Error parsing birth date: " + e.getMessage());
                }
            }

            userProfile.setPhone(registerDto.profile().phone());
            userProfile.setResidence(registerDto.profile().residence());
        }

        // Create UserBaseball if baseball data is provided
        UserBaseball userBaseball = null;
        if (registerDto.baseball() != null) {
            userBaseball = new UserBaseball();
            userBaseball.setUserId(0); // Will be set after user is saved

            // Extract baseball data
            if (registerDto.baseball().teamId() != null) {
                userBaseball.setTeamId(registerDto.baseball().teamId());
            }

            if (registerDto.baseball().jerseyNo() != null) {
                userBaseball.setJerseyNo(registerDto.baseball().jerseyNo());
            }

            userBaseball.setThrowingHand(registerDto.baseball().throwingHand());
            userBaseball.setBattingHand(registerDto.baseball().battingHand());
        }

        // Create UserPositions if position data is provided
        List<UserPositions> userPositions = null;
        List<Integer> positionIds = registerDto.position() != null ? registerDto.position().positionIds() : null;
        if (positionIds != null && !positionIds.isEmpty()) {
            // If userBaseball is null but positions are provided, create a basic UserBaseball entity
            if (userBaseball == null) {
                userBaseball = new UserBaseball();
                userBaseball.setUserId(0); // Will be set after user is saved
            }

            // Create UserPositions for each position ID
            userPositions = new java.util.ArrayList<>();
            for (Integer positionId : positionIds) {
                UserPositions userPosition = new UserPositions();
                userPosition.setUserBaseballId(0); // Will be set after userBaseball is saved
                userPosition.setPositionId(positionId);
                userPositions.add(userPosition);
            }
        }

        // Save the user with all its related entities
        return userRepository.saveWithRelationships(user, userProfile, userBaseball, userPositions);
    }


    @Override
    public JwtToken login(String email, String password) {
        return jwtService.signIn(email, password);
    }

    @Override
    public JwtToken refreshToken(String refreshToken) {
        return jwtService.refreshToken(refreshToken);
    }

    @Override
    public boolean logout(String refreshToken) {
        // 1. Refresh Token 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ResponseCode.INVALID_TOKEN, ResponseCode.INVALID_TOKEN.getMessage());
        }


        if (!redisService.hasKey(refreshToken)) {
            return false; // 이미 삭제되었거나 존재하지 않음
        }

        redisService.deleteValues(refreshToken);
        return true; // 삭제 성공
    }

    @Override
    public Users registerAdmin(AdminRegisterDto adminRegisterDto) {
        // Extract basic user information
        String username = adminRegisterDto.username();
        String email = adminRegisterDto.email();
        String password = adminRegisterDto.password();

        // Check if username or email already exists
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // Create new admin user
        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole("ADMIN");
        user.setAdminApproved(true);

        // Create UserProfiles if profile data is provided
        UserProfiles userProfile = null;
        if (adminRegisterDto.profile() != null) {
            userProfile = new UserProfiles();
            userProfile.setUserId(0); // Will be set after user is saved

            // Extract profile data
            userProfile.setFullName(adminRegisterDto.profile().fullName());

            // Convert string date to SQL Date if provided
            String birthDateStr = adminRegisterDto.profile().birthDate();
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                try {
                    LocalDate localDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_DATE);
                    userProfile.setBirthDate(Date.valueOf(localDate));
                } catch (Exception e) {
                    // Handle date parsing error
                    System.err.println("Error parsing birth date: " + e.getMessage());
                }
            }

            userProfile.setPhone(adminRegisterDto.profile().phone());
            userProfile.setResidence(adminRegisterDto.profile().residence());
        }

        // Create UserBaseball if baseball data is provided
        UserBaseball userBaseball = null;
        if (adminRegisterDto.baseball() != null) {
            userBaseball = new UserBaseball();
            userBaseball.setUserId(0); // Will be set after user is saved

            // Extract baseball data
            if (adminRegisterDto.baseball().teamId() != null) {
                userBaseball.setTeamId(adminRegisterDto.baseball().teamId());
            }

            if (adminRegisterDto.baseball().jerseyNo() != null) {
                userBaseball.setJerseyNo(adminRegisterDto.baseball().jerseyNo());
            }

            userBaseball.setThrowingHand(adminRegisterDto.baseball().throwingHand());
            userBaseball.setBattingHand(adminRegisterDto.baseball().battingHand());
        }

        // Create UserPositions if position data is provided
        List<UserPositions> userPositions = null;
        List<Integer> positionIds = adminRegisterDto.position() != null ? adminRegisterDto.position().positionIds() : null;
        if (positionIds != null && !positionIds.isEmpty()) {
            // If userBaseball is null but positions are provided, create a basic UserBaseball entity
            if (userBaseball == null) {
                userBaseball = new UserBaseball();
                userBaseball.setUserId(0); // Will be set after user is saved
            }

            // Create UserPositions for each position ID
            userPositions = new java.util.ArrayList<>();
            for (Integer positionId : positionIds) {
                UserPositions userPosition = new UserPositions();
                userPosition.setUserBaseballId(0); // Will be set after userBaseball is saved
                userPosition.setPositionId(positionId);
                userPositions.add(userPosition);
            }
        }

        // Save the user with all its related entities
        return userRepository.saveWithRelationships(user, userProfile, userBaseball, userPositions);
    }

    @Override
    public Users updateAdminApproval(AdminApprovalDto adminApprovalDto) {
        String username = adminApprovalDto.username();
        boolean approved = adminApprovalDto.approved();

        // Find the user by username
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Update admin approval status
        user.setAdminApproved(approved);

        // Save and return the updated user
        return userRepository.save(user);
    }
}
