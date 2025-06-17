package io.github.ruifoot.infrastructure.persistence.entity.enums;

public enum PositionCode {

    P("P"),
    C("C"),
    _1B("1B"),
    _2B("2B"),
    _3B("3B"),
    SS("SS"),
    LF("LF"),
    CF("CF"),
    RF("RF"),
    DH("DH"),
    CO("CO"),
    MG("MG");

    private final String dbValue;

    PositionCode(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static PositionCode fromDbValue(String dbValue) {
        for (PositionCode code : values()) {
            if (code.dbValue.equals(dbValue)) {
                return code;
            }
        }
        throw new IllegalArgumentException("Unknown dbValue: " + dbValue);
    }
}