package com.hardnets.coop.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "dropdownlist")
public class DropDownListEntity extends BaseEntity {


    @Column(nullable = false)
    private String value;

    @Column
    private String code;

    @Column(name = "list_type")
    private String dropDownListType;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private DropDownListEntity parent;

    /**
     * Lista de tipo de cobros relacionados a un tipo de usuario
     */
    @JsonIgnore
    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEntity> items;
}
