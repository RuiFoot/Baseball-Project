package io.github.ruifoot.infrastructure.persistence.entity.notice;

import io.github.ruifoot.infrastructure.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tags")
@Getter
@Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class TagsEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @OneToMany(mappedBy = "tagsEntity")
    @Builder.Default
    private List<NoticeEntity> noticeEntities = new ArrayList<>();

}
