package com.princevelasco.platform.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(appliesTo = "code")
public class Code {
    @Id
    @JsonIgnore
    @GeneratedValue(generator = "UUID")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    UUID id;
    @Column
    String code;
    @Column
    LocalDateTime date;
    @Column
    Long time = 0L;
    @Column
    Integer views = 0;
    @JsonIgnore
    @Column
    Boolean isTimeRestricted = false;
    @JsonIgnore
    @Column
    Boolean isViewRestricted = false;
    @JsonIgnore
    @Transient
    Boolean isToBeDeleted = false;

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return formatter.format(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Code code = (Code) o;
        return id != null && Objects.equals(id, code.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
