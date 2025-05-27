package io.github.ruifoot.infrastructure.persistence.entity;

import io.github.ruifoot.infrastructure.InfrastructureTestApplication;
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
        // given
        Position position = new Position();
        position.setCode(PositionCode.P);
        position.setNameKr("투수");
        position.setNameEn("Pitcher");
        position.setCategory(PositionCategory.배터리);

        // Log input values
        log.info("[DEBUG_LOG] Position entity to persist: code={}, nameKr={}, nameEn={}, category={}", 
                position.getCode(), 
                position.getNameKr(), 
                position.getNameEn(), 
                position.getCategory());

        // when
        entityManager.persist(position);
        entityManager.flush();
        entityManager.clear();

        log.info("[DEBUG_LOG] Position entity persisted with ID: {}", position.getId());

        // then
        Position foundPosition = entityManager.find(Position.class, position.getId());
        assertThat(foundPosition).isNotNull();
        assertThat(foundPosition.getCode()).isEqualTo(PositionCode.P);
        assertThat(foundPosition.getNameKr()).isEqualTo("투수");
        assertThat(foundPosition.getNameEn()).isEqualTo("Pitcher");
        assertThat(foundPosition.getCategory()).isEqualTo(PositionCategory.배터리);

        // Log retrieved entity
        log.info("[DEBUG_LOG] Position entity retrieved: id={}, code={}, nameKr={}, nameEn={}, category={}", 
                foundPosition.getId(),
                foundPosition.getCode(), 
                foundPosition.getNameKr(), 
                foundPosition.getNameEn(), 
                foundPosition.getCategory());
    }

    @Test
    public void testPositionEntityWithNullNameEn() {
        // given
        Position position = new Position();
        position.setCode(PositionCode.C);
        position.setNameKr("포수");
        position.setNameEn(null); // nameEn is optional
        position.setCategory(PositionCategory.배터리);

        log.info("[DEBUG_LOG] Testing Position with null nameEn");

        // when
        entityManager.persist(position);
        entityManager.flush();
        entityManager.clear();

        // then
        Position foundPosition = entityManager.find(Position.class, position.getId());
        assertThat(foundPosition).isNotNull();
        assertThat(foundPosition.getNameEn()).isNull();
        log.info("[DEBUG_LOG] Successfully persisted and retrieved Position with null nameEn");
    }

    @Test
    public void testPositionEntityWithNullCategory() {
        // given
        Position position = new Position();
        position.setCode(PositionCode._1B);
        position.setNameKr("1루수");
        position.setNameEn("First Baseman");
        position.setCategory(null); // category is optional

        log.info("[DEBUG_LOG] Testing Position with null category");

        // when
        entityManager.persist(position);
        entityManager.flush();
        entityManager.clear();

        // then
        Position foundPosition = entityManager.find(Position.class, position.getId());
        assertThat(foundPosition).isNotNull();
        assertThat(foundPosition.getCategory()).isNull();
        log.info("[DEBUG_LOG] Successfully persisted and retrieved Position with null category");
    }

    @Test
    public void testPositionEntityWithMaxLengthStrings() {
        // given
        String maxLengthString = "a".repeat(50); // 50 characters

        Position position = new Position();
        position.setCode(PositionCode.SS);
        position.setNameKr(maxLengthString);
        position.setNameEn(maxLengthString);
        position.setCategory(PositionCategory.내야수);

        log.info("[DEBUG_LOG] Testing Position with max length strings (50 chars)");

        // when
        entityManager.persist(position);
        entityManager.flush();
        entityManager.clear();

        // then
        Position foundPosition = entityManager.find(Position.class, position.getId());
        assertThat(foundPosition).isNotNull();
        assertThat(foundPosition.getNameKr()).hasSize(50);
        assertThat(foundPosition.getNameEn()).hasSize(50);
        log.info("[DEBUG_LOG] Successfully persisted and retrieved Position with max length strings");
    }

    @Test
    public void testPositionEntityWithNullNameKr() {
        // given
        Position position = new Position();
        position.setCode(PositionCode.RF);
        position.setNameKr(null); // nameKr is required, should cause an exception
        position.setNameEn("Right Fielder");
        position.setCategory(PositionCategory.외야수);

        log.info("[DEBUG_LOG] Testing Position with null nameKr (should fail)");

        // when & then
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(position);
            entityManager.flush();
        });

        log.info("[DEBUG_LOG] Successfully caught exception for null nameKr");
    }

    @ParameterizedTest
    @EnumSource(PositionCode.class)
    public void testPositionEntityWithDifferentCodes(PositionCode code) {
        // given
        Position position = new Position();
        position.setCode(code);
        position.setNameKr("포지션 " + code);
        position.setNameEn("Position " + code);
        position.setCategory(PositionCategory.코칭스태프); // Just using a fixed category for simplicity

        log.info("[DEBUG_LOG] Testing Position with code: {}", code);

        // when
        entityManager.persist(position);
        entityManager.flush();
        entityManager.clear();

        // then
        Position foundPosition = entityManager.find(Position.class, position.getId());
        assertThat(foundPosition).isNotNull();
        assertThat(foundPosition.getCode()).isEqualTo(code);
        log.info("[DEBUG_LOG] Successfully persisted and retrieved Position with code: {}", code);
    }
}
