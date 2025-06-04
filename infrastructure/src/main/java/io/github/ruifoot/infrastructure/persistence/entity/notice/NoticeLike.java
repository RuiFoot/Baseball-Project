package io.github.ruifoot.infrastructure.persistence.entity.notice;

import io.github.ruifoot.infrastructure.persistence.entity.BaseTimeEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notice_likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"notice_id", "user_id"})
})
@Getter
@Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class NoticeLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 대상 공지사항
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    // 좋아요 누른 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}