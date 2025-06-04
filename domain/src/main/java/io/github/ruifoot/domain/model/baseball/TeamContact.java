package io.github.ruifoot.domain.model.baseball;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 연락처, 이메일, 주소
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TeamContact extends BaseTimeDomain {
    private Long id;
    private String phoneNumber;
    private String email;
    private String address;
    private String imageUrl;
    private Teams teams;
}