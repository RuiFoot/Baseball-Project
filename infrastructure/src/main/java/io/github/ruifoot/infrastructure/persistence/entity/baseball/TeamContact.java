package io.github.ruifoot.infrastructure.persistence.entity.baseball;

import io.github.ruifoot.infrastructure.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 연락처, 이메일, 주소
 */
@Getter
@Setter
@Entity
@Table(name = "team_contacts")
public class TeamContact extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "image_url")
    private String imageUrl; // ex: "/images/logo.png"

    @OneToOne
    @JoinColumn(name = "team_id")
    private Teams teams;
}
