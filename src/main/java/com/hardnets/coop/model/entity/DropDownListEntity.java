package com.hardnets.coop.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "dropdownlist")
public class DropDownListEntity extends BaseEntity {

    @OneToMany(mappedBy = "size", fetch = FetchType.LAZY)
    @JsonBackReference
    Set<WaterMeterEntity> waterMeters;
    @Column(nullable = false)
    private String value;
    @Column
    private String code;
    @Column(name = "list_type")
    private String dropDownListType;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private DropDownListEntity parent;
}
