package io.github.ruifoot.domain.service.user;

public interface UserService {

    String getUsername(long id);
    String getUsername(String email);

}
