package io.github.ruifoot.infrastructure.persistence.mapper.baseball;

import io.github.ruifoot.infrastructure.persistence.entity.enums.PositionCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PositionCodeConverter implements AttributeConverter<PositionCode, String> {

    @Override
    public String convertToDatabaseColumn(PositionCode attribute) {
        return attribute != null ? attribute.getDbValue() : null;
    }

    @Override
    public PositionCode convertToEntityAttribute(String dbData) {
        return dbData != null ? PositionCode.fromDbValue(dbData) : null;
    }
}