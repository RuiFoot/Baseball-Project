package io.github.ruifoot.domain.model.user;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserProfiles extends BaseTimeDomain {

  private long id;
  private long userId;
  private String fullName;
  private java.sql.Date birthDate;
  private String phone;
  private String residence;



}
