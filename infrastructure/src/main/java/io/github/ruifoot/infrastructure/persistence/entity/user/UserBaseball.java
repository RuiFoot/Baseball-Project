package io.github.ruifoot.infrastructure.persistence.entity.user;

import io.github.ruifoot.infrastructure.persistence.entity.BaseTimeEntity;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.Teams;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "user_baseball")
public class UserBaseball extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "team_id")
    private Teams teams;

    @Column(name = "jersey_no")
    private Integer jerseyNo;

    @Size(max = 10)
    @Column(name = "throwing_hand", length = 10)
    private String throwingHand;

    @Size(max = 10)
    @Column(name = "batting_hand", length = 10)
    private String battingHand;

}