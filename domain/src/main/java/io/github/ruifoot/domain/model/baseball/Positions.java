package io.github.ruifoot.domain.model.baseball;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import io.github.ruifoot.domain.model.baseball.enums.PositionCategory;
import io.github.ruifoot.domain.model.baseball.enums.PositionCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Positions extends BaseTimeDomain {

  private long id;
  private PositionCode code;
  private String nameKr;
  private String nameEn;
  private PositionCategory category;

}
