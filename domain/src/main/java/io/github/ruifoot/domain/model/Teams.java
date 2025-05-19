package io.github.ruifoot.domain.model;


import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
public class Teams {

  private long id;
  private String name;
  private Date foundedDate;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;

}
