package io.github.ruifoot.infrastructure.persistence.entity.notice;


import io.github.ruifoot.infrastructure.persistence.entity.BaseTimeEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notice_view_logs", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"notice_id", "viewer_id"})
})
@Getter
@Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class NoticeViewLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 대상 공지사항
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    // 열람한 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewer_id", nullable = false)
    private User viewer;
}