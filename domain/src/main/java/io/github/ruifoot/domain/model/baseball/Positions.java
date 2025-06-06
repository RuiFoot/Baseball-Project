package io.github.ruifoot.domain.model.baseball;

import io.github.ruifoot.domain.model.baseball.enums.PositionCategory;
import io.github.ruifoot.domain.model.baseball.enums.PositionCode;
import lombok.Data;

@Data
public class Positions {

  private long id;
  private PositionCode code;
  private String nameKr;
  private String nameEn;
  private PositionCategory category;

}
