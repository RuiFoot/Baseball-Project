package io.github.ruifoot.infrastructure.persistence.repository.jpa;

import io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeJpaRepository extends JpaRepository<NoticeEntity, Long> {


    @Modifying
    @Query("update NoticeEntity n set n.viewCount = n.viewCount + 1 where n.id = :noticeId")
    int incrementViewCount(@Param("noticeId") Long noticeId);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        update NoticeEntity n
           set n.title = :title,
               n.preview = :preview,
               n.content = :content,
               n.tagsEntity.id = :tagId,
               n.updatedAt = CURRENT_TIMESTAMP
         where n.id = :noticeId
           and n.author.username = :username
    """)
    int updateNotice(@Param("noticeId") Long noticeId,
                     @Param("title") String title,
                     @Param("preview") String preview,
                     @Param("content") String content,
                     @Param("tagId") Long tagId,
                     @Param("username") String username);


    @Modifying
    @Query("delete from NoticeEntity n where n.id = :noticeId and n.author.username = :username")
    int deleteByIdAndAuthorUsername(@Param("noticeId") Long noticeId,
                                    @Param("username") String username);
}
