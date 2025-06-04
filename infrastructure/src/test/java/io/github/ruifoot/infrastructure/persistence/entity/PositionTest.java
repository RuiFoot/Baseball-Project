package io.github.ruifoot.infrastructure.persistence.entity;

import io.github.ruifoot.infrastructure.InfrastructureTestApplication;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.Position;
import io.github.ruifoot.infrastructure.persistence.entity.enums.PositionCategory;
import io.github.ruifoot.infrastructure.persistence.entity.enums.PositionCode;
import io.github.ruifoot.infrastructure.test.BaseTest;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
@ContextConfiguration(classes = InfrastructureTestApplication.class)
@ActiveProfiles("test")
public class PositionTest extends BaseTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testPositionEntity() {
        // 준비
        Position position = new Position();
        position.setCode(PositionCode.P);
        position.setNameKr("투수");
        position.setNameEn("Pitcher");
        position.setCategory(PositionCategory.배터리);

        // 입력 값 로그
        log.info("[DEBUG_LOG] 저장할 Position 엔티티: code={}, nameKr={}, nameEn={}, category={}", 
                position.getCode(), 
                position.getNameKr(), 
                position.getNameEn(), 
                position.getCategory());

        // 실행
        entityManager.persist(position);
        entityManager.flush();
        entityManager.clear();

        log.info("[DEBUG_LOG] ID가 {}인 Position 엔티티 저장됨", position.getId());

        // 검증
        Position foundPosition = entityManager.find(Position.class, position.getId());
        assertThat(foundPosition).isNotNull();
        assertThat(foundPosition.getCode()).isEqualTo(PositionCode.P);
        assertThat(foundPosition.getNameKr()).isEqualTo("투수");
        assertThat(foundPosition.getNameEn()).isEqualTo("Pitcher");
        assertThat(foundPosition.getCategory()).isEqualTo(PositionCategory.배터리);

        // 조회된 엔티티 로그
        log.info("[DEBUG_LOG] 조회된 Position 엔티티: id={}, code={}, nameKr={}, nameEn={}, category={}", 
                foundPosition.getId(),
                foundPosition.getCode(), 
                foundPosition.getNameKr(), 
                foundPosition.getNameEn(), 
                foundPosition.getCategory());
    }

    @Test
    public void testPositionEntityWithNullNameEn() {
        // 준비
        Position position = new Position();
        position.setCode(PositionCode.C);
        position.setNameKr("포수");
        position.setNameEn(null); // nameEn은 선택 사항
        position.setCategory(PositionCategory.배터리);

        log.info("[DEBUG_LOG] nameEn이 null인 Position 테스트 중");

        // 실행
        entityManager.persist(position);
        entityManager.flush();
        entityManager.clear();

        // 검증
        Position foundPosition = entityManager.find(Position.class, position.getId());
        assertThat(foundPosition).isNotNull();
        assertThat(foundPosition.getNameEn()).isNull();
        log.info("[DEBUG_LOG] nameEn이 null인 Position을 성공적으로 저장하고 조회함");
    }

    @Test
    public void testPositionEntityWithNullCategory() {
        // 준비
        Position position = new Position();
        position.setCode(PositionCode._1B);
        position.setNameKr("1루수");
        position.setNameEn("First Baseman");
        position.setCategory(null); // category는 선택 사항

        log.info("[DEBUG_LOG] category가 null인 Position 테스트 중");

        // 실행
        entityManager.persist(position);
        entityManager.flush();
        entityManager.clear();

        // 검증
        Position foundPosition = entityManager.find(Position.class, position.getId());
        assertThat(foundPosition).isNotNull();
        assertThat(foundPosition.getCategory()).isNull();
        log.info("[DEBUG_LOG] category가 null인 Position을 성공적으로 저장하고 조회함");
    }

    @Test
    public void testPositionEntityWithMaxLengthStrings() {
        // 준비
        String maxLengthString = "a".repeat(50); // 50자

        Position position = new Position();
        position.setCode(PositionCode.SS);
        position.setNameKr(maxLengthString);
        position.setNameEn(maxLengthString);
        position.setCategory(PositionCategory.내야수);

        log.info("[DEBUG_LOG] 최대 길이 문자열(50자)로 Position 테스트 중");

        // 실행
        entityManager.persist(position);
        entityManager.flush();
        entityManager.clear();

        // 검증
        Position foundPosition = entityManager.find(Position.class, position.getId());
        assertThat(foundPosition).isNotNull();
        assertThat(foundPosition.getNameKr()).hasSize(50);
        assertThat(foundPosition.getNameEn()).hasSize(50);
        log.info("[DEBUG_LOG] 최대 길이 문자열을 가진 Position을 성공적으로 저장하고 조회함");
    }

    @Test
    public void testPositionEntityWithNullNameKr() {
        // 준비
        Position position = new Position();
        position.setCode(PositionCode.RF);
        position.setNameKr(null); // nameKr은 필수, 예외가 발생해야 함
        position.setNameEn("Right Fielder");
        position.setCategory(PositionCategory.외야수);

        log.info("[DEBUG_LOG] nameKr이 null인 Position 테스트 중 (실패해야 함)");

        // 실행 & 검증
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(position);
            entityManager.flush();
        });

        log.info("[DEBUG_LOG] null nameKr에 대한 예외를 성공적으로 포착함");
    }

    @ParameterizedTest
    @EnumSource(PositionCode.class)
    public void testPositionEntityWithDifferentCodes(PositionCode code) {
        // 준비
        Position position = new Position();
        position.setCode(code);
        position.setNameKr("포지션 " + code);
        position.setNameEn("Position " + code);
        position.setCategory(PositionCategory.코칭스태프); // 간단하게 하기 위해 고정된 카테고리 사용

        log.info("[DEBUG_LOG] 코드가 {}인 Position 테스트 중", code);

        // 실행
        entityManager.persist(position);
        entityManager.flush();
        entityManager.clear();

        // 검증
        Position foundPosition = entityManager.find(Position.class, position.getId());
        assertThat(foundPosition).isNotNull();
        assertThat(foundPosition.getCode()).isEqualTo(code);
        log.info("[DEBUG_LOG] 코드가 {}인 Position을 성공적으로 저장하고 조회함", code);
    }
}
