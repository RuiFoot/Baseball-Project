package io.github.ruifoot.infrastructure.persistence.entity.notice;

import io.github.ruifoot.infrastructure.persistence.entity.BaseTimeEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotBlank
    @Column(nullable = false)
    private String content;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Users author;

    // 대상 공지사항
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;
}