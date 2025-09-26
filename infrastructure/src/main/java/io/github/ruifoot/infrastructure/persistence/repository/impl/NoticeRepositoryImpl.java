package io.github.ruifoot.infrastructure.persistence.repository.impl;


import io.github.ruifoot.common.dto.notice.response.NoticeResponseDto;
import io.github.ruifoot.domain.repository.NoticeRepository;
import io.github.ruifoot.infrastructure.persistence.mapper.notice.NoticeMapper;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.NoticeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {


    private final NoticeJpaRepository noticeJpaRepository;
    private final NoticeMapper noticeMapper;

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
}
