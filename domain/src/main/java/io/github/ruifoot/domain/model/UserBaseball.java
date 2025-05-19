package io.github.ruifoot.domain.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserBaseball extends BaseTimeDomain {

  private long id;
  private long userId;
  private long teamId;
  private long jerseyNo;

  private String throwingHand;
  private String battingHand;


}
