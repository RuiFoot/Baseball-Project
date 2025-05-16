package io.github.ruifoot.domain.user.model;


public record User(
        Long id,
        String name
) {
    // User 클래스는 사용자 정보를 나타내는 데이터 전송 객체(DTO)입니다.
    // 이 클래스는 사용자의 ID와 이름을 포함하고 있습니다.
    // Lombok 라이브러리를 사용하여 getter, setter, 생성자 등을 자동으로 생성합니다.
    // 이 클래스는 불변성을 유지하기 위해 record로 정의되었습니다.
}
