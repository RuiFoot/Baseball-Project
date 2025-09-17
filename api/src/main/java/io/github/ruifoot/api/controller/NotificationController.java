package io.github.ruifoot.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 실시간 알림 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    /*
    TODO[NotificationController]: 알림 구독 엔드포인트 (GET /notifications/subscribe)
     - 설명: 클라이언트가 서버로부터 실시간 알림을 받기 위해 연결을 수립합니다. (SSE 방식 추천)
    */

    /*
    TODO[NotificationController]: (백엔드) 알림 생성 및 발송 로직
     - 설명: 주요 이벤트(팀 가입 신청, 경기 일정 등록 등) 발생 시 알림을 생성하여 DB에 저장하고, 구독 중인 클라이언트에게 발송합니다.
    */

    /*
    TODO[NotificationController]: 사용자의 알림 목록 조회 (GET /notifications)
     - 설명: 사용자가 받은 모든 알림 목록을 조회합니다.
    */

    /*
    TODO[NotificationController]: 알림 읽음 처리 (PUT /notifications/{notificationId}/read)
     - 설명: 사용자가 특정 알림을 읽었음을 서버에 알립니다.
    */
}
