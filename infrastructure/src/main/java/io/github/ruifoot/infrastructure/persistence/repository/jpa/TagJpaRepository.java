package io.github.ruifoot.infrastructure.persistence.repository.jpa;

import io.github.ruifoot.infrastructure.persistence.entity.notice.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagJpaRepository extends JpaRepository<TagsEntity, Long> {

}
