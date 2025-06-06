package io.github.ruifoot.domain.model.baseball;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Teams extends BaseTimeDomain {

  private long id;
  private String name;
  private LocalDate foundedDate;
  private TeamContact contact;
  private TeamInfo teamInfo;
  private List<TeamHistory> histories;
  private List<TeamRule> rules;

}
