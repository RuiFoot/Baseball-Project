package io.github.ruifoot.infrastructure.persistence.entity.notice;

import io.github.ruifoot.infrastructure.persistence.entity.BaseTimeEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notices")
@Getter
@Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @NotBlank
    @Column(nullable = false)
    private String content;

    @Builder.Default
    @Column(nullable = false)
    private int viewCount = 0;

    @Builder.Default
    @Column(nullable = false)
    private boolean isPinned = false;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Users author;

    @ManyToMany
    @JoinTable(
            name = "notice_tags",
            joinColumns = @JoinColumn(name = "notice_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();
}
