package io.github.ruifoot.infrastructure.persistence.entity;

import io.github.ruifoot.infrastructure.InfrastructureTestApplication;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.Positions;
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
public class PositionsTest extends BaseTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testPositionEntity() {
        // 준비
        Positions positions = new Positions();
        positions.setCode(PositionCode.P);
        positions.setNameKr("투수");
        positions.setNameEn("Pitcher");
        positions.setCategory(PositionCategory.배터리);

        // 입력 값 로그
        log.info("[DEBUG_LOG] 저장할 Position 엔티티: code={}, nameKr={}, nameEn={}, category={}", 
                positions.getCode(),
                positions.getNameKr(),
                positions.getNameEn(),
                positions.getCategory());

        // 실행
        entityManager.persist(positions);
        entityManager.flush();
        entityManager.clear();

        log.info("[DEBUG_LOG] ID가 {}인 Position 엔티티 저장됨", positions.getId());

        // 검증
        Positions foundPositions = entityManager.find(Positions.class, positions.getId());
        assertThat(foundPositions).isNotNull();
        assertThat(foundPositions.getCode()).isEqualTo(PositionCode.P);
        assertThat(foundPositions.getNameKr()).isEqualTo("투수");
        assertThat(foundPositions.getNameEn()).isEqualTo("Pitcher");
        assertThat(foundPositions.getCategory()).isEqualTo(PositionCategory.배터리);

        // 조회된 엔티티 로그
        log.info("[DEBUG_LOG] 조회된 Position 엔티티: id={}, code={}, nameKr={}, nameEn={}, category={}", 
                foundPositions.getId(),
                foundPositions.getCode(),
                foundPositions.getNameKr(),
                foundPositions.getNameEn(),
                foundPositions.getCategory());
    }

    @Test
    public void testPositionEntityWithNullNameEn() {
        // 준비
        Positions positions = new Positions();
        positions.setCode(PositionCode.C);
        positions.setNameKr("포수");
        positions.setNameEn(null); // nameEn은 선택 사항
        positions.setCategory(PositionCategory.배터리);

        log.info("[DEBUG_LOG] nameEn이 null인 Position 테스트 중");

        // 실행
        entityManager.persist(positions);
        entityManager.flush();
        entityManager.clear();

        // 검증
        Positions foundPositions = entityManager.find(Positions.class, positions.getId());
        assertThat(foundPositions).isNotNull();
        assertThat(foundPositions.getNameEn()).isNull();
        log.info("[DEBUG_LOG] nameEn이 null인 Position을 성공적으로 저장하고 조회함");
    }

    @Test
    public void testPositionEntityWithNullCategory() {
        // 준비
        Positions positions = new Positions();
        positions.setCode(PositionCode._1B);
        positions.setNameKr("1루수");
        positions.setNameEn("First Baseman");
        positions.setCategory(null); // category는 선택 사항

        log.info("[DEBUG_LOG] category가 null인 Position 테스트 중");

        // 실행
        entityManager.persist(positions);
        entityManager.flush();
        entityManager.clear();

        // 검증
        Positions foundPositions = entityManager.find(Positions.class, positions.getId());
        assertThat(foundPositions).isNotNull();
        assertThat(foundPositions.getCategory()).isNull();
        log.info("[DEBUG_LOG] category가 null인 Position을 성공적으로 저장하고 조회함");
    }

    @Test
    public void testPositionEntityWithMaxLengthStrings() {
        // 준비
        String maxLengthString = "a".repeat(50); // 50자

        Positions positions = new Positions();
        positions.setCode(PositionCode.SS);
        positions.setNameKr(maxLengthString);
        positions.setNameEn(maxLengthString);
        positions.setCategory(PositionCategory.내야수);

        log.info("[DEBUG_LOG] 최대 길이 문자열(50자)로 Position 테스트 중");

        // 실행
        entityManager.persist(positions);
        entityManager.flush();
        entityManager.clear();

        // 검증
        Positions foundPositions = entityManager.find(Positions.class, positions.getId());
        assertThat(foundPositions).isNotNull();
        assertThat(foundPositions.getNameKr()).hasSize(50);
        assertThat(foundPositions.getNameEn()).hasSize(50);
        log.info("[DEBUG_LOG] 최대 길이 문자열을 가진 Position을 성공적으로 저장하고 조회함");
    }

    @Test
    public void testPositionEntityWithNullNameKr() {
        // 준비
        Positions positions = new Positions();
        positions.setCode(PositionCode.RF);
        positions.setNameKr(null); // nameKr은 필수, 예외가 발생해야 함
        positions.setNameEn("Right Fielder");
        positions.setCategory(PositionCategory.외야수);

        log.info("[DEBUG_LOG] nameKr이 null인 Position 테스트 중 (실패해야 함)");

        // 실행 & 검증
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(positions);
            entityManager.flush();
        });

        log.info("[DEBUG_LOG] null nameKr에 대한 예외를 성공적으로 포착함");
    }

    @ParameterizedTest
    @EnumSource(PositionCode.class)
    public void testPositionEntityWithDifferentCodes(PositionCode code) {
        // 준비
        Positions positions = new Positions();
        positions.setCode(code);
        positions.setNameKr("포지션 " + code);
        positions.setNameEn("Position " + code);
        positions.setCategory(PositionCategory.코칭스태프); // 간단하게 하기 위해 고정된 카테고리 사용

        log.info("[DEBUG_LOG] 코드가 {}인 Position 테스트 중", code);

        // 실행
        entityManager.persist(positions);
        entityManager.flush();
        entityManager.clear();

        // 검증
        Positions foundPositions = entityManager.find(Positions.class, positions.getId());
        assertThat(foundPositions).isNotNull();
        assertThat(foundPositions.getCode()).isEqualTo(code);
        log.info("[DEBUG_LOG] 코드가 {}인 Position을 성공적으로 저장하고 조회함", code);
    }
}
