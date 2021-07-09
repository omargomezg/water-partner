package com.hardnets.coop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
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

    @OneToMany(mappedBy = "decree", fetch = FetchType.LAZY)
    private Set<SubsidyEntity> subsidies;

}
