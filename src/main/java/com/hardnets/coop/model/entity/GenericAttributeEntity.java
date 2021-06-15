package com.hardnets.coop.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardnets.coop.model.constant.AttributeKeyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@Data
@Entity
@Table(name = "attribute")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GenericAttributeEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private AttributeKeyEnum key;

    private String value;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private CompanyEntity company;
}

