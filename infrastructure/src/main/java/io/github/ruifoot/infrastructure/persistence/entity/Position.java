package io.github.ruifoot.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "positions")
public class Position {
    @Id
    @ColumnDefault("nextval('positions_id_seq')")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name_kr", nullable = false, length = 50)
    private String nameKr;
    @Size(max = 50)
    @Column(name = "name_en", length = 50)
    private String nameEn;

/*
TODO [리버스 엔지니어링] 필드를 생성하여 'code' 열에 매핑
사용 가능한 액션: 대상 Java 타입 정의 | 현재 상태대로 주석 해제 | 열 매핑 제거
    @Column(name = "code", columnDefinition = "position_code not null")
    private Object code;
*/
/*
TODO [리버스 엔지니어링] 필드를 생성하여 'category' 열에 매핑
사용 가능한 액션: 대상 Java 타입 정의 | 현재 상태대로 주석 해제 | 열 매핑 제거
    @Column(name = "category", columnDefinition = "position_category not null")
    private Object category;
*/
}