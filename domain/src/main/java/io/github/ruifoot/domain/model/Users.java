package io.github.ruifoot.domain.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Users extends BaseTimeDomain {

  private long id;
  private String username;
  private String email;
  private String passwordHash;
  private String role;



}
