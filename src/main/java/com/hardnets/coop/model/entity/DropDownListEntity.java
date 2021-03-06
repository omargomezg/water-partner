package com.hardnets.coop.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
