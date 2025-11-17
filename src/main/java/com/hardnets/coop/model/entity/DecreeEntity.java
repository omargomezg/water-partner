package com.hardnets.coop.model.entity;

import lombok.Data;
import org.hibernate.Hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "decree")
public class DecreeEntity extends BaseEntity {
    /**
     * Número de decreto
     */
    private Integer number;

    /**
     * Fecha de publicación del decreto
     */
    private Date approved;

    @OneToMany(mappedBy = "decree", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<SubsidyEntity> subsidies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DecreeEntity that = (DecreeEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
