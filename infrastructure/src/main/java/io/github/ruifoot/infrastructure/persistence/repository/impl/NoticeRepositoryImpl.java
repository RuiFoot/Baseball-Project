package io.github.ruifoot.infrastructure.persistence.repository.impl;


import io.github.ruifoot.common.dto.notice.request.NoticeRequestDto;
import io.github.ruifoot.common.dto.notice.response.NoticeDetailResponseDto;
import io.github.ruifoot.common.dto.notice.response.NoticeResponseDto;
import io.github.ruifoot.domain.model.notice.Tag;
import io.github.ruifoot.domain.repository.NoticeRepository;
import io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeEntity;
import io.github.ruifoot.infrastructure.persistence.entity.notice.TagsEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.UsersEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.notice.NoticeMapper;
import io.github.ruifoot.infrastructure.persistence.mapper.notice.TagMapper;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.NoticeJpaRepository;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.TagJpaRepository;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {


    private final NoticeJpaRepository noticeJpaRepository;
    private final TagJpaRepository tagJpaRepository;
    private final NoticeMapper noticeMapper;
    private final TagMapper tagMapper;
    private final UserJpaRepository userJpaRepository;

    @Override
    public Page<NoticeResponseDto> findNotices(Pageable pageable) {

        /*
    TODO[NoticeRepository]: 공지사항 목록 조회 (GET /)
     - 불필요한 정보 정리 필요 -> 정보에 맞는 Response 필요 : 정리를 1차적으로 했으나 추가 정리 필요할지도?
     - 이름 -> fullName으로 해야함
     - 다양한 데이터를 추가했을 경우 정렬이 잘 되는지 확인 필요
     - Response: Page<?>
    */


        return noticeJpaRepository.findAll(pageable).map(noticeMapper :: toResponseDto);

    }

    @Override
    public List<Tag> findTags() {

        return Optional.of(tagJpaRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new EntityNotFoundException("태그가 존재하지 않습니다."))
                .stream().map(tagMapper :: toDomain).toList();
    }

    @Override
    public Tag findTagById(long id) {
        return tagJpaRepository.findById(id)
                .map(tagMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Tag not found")); // 없으면 예외
    }

    @Override
    public NoticeDetailResponseDto findNoticeDetail(Long noticeId) {
        return noticeJpaRepository.findById(noticeId)
                .map(noticeMapper::toDetailResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다."));
    }

    public NoticeDetailResponseDto save(NoticeRequestDto noticeRequestDto, UserDetails user) {

        UsersEntity authorEntity = userJpaRepository.getReferenceByUsername((user.getUsername()));

        TagsEntity tagsEntity = tagJpaRepository.getReferenceById(noticeRequestDto.tagId());

        NoticeEntity entity = NoticeEntity.builder()
                .title(noticeRequestDto.title())
                .preview(noticeRequestDto.preview())
                .content(noticeRequestDto.content())
                .author(authorEntity)
                .tagsEntity(tagsEntity)
                .viewCount(0)
                .isPinned(false)
                .build();

        return noticeMapper.toDetailResponseDto(noticeJpaRepository.save(entity));
    }

}
