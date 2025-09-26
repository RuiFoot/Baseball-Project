package io.github.ruifoot.infrastructure.persistence.entity.notice;

import io.github.ruifoot.infrastructure.persistence.entity.BaseTimeEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.UsersEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "notices")
@Getter
@Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class NoticeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String title;  // 제목

    @Column(length = 100)
    private String preview;  // 요약

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 내용

    @Builder.Default
    @Column(nullable = false)
    private int viewCount = 0;

    @Builder.Default
    @Column(nullable = false)
    private boolean isPinned = false;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UsersEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "tag_id")
    private TagsEntity tagsEntity;


}
