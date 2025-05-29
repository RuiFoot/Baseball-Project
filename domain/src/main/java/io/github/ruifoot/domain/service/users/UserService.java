package io.github.ruifoot.domain.service.users;

public interface UserService {

    String getUsername(long id);
    String getUsername(String email);

}
