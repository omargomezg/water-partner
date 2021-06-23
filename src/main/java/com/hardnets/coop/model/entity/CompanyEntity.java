package com.hardnets.coop.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuraciones para una determinada entidad, lo que permitiría tener varios cliente bajo un mismo sistema.
 *
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@Getter
@Setter
@Entity
@Table(name = "company")
public class CompanyEntity extends BaseEntity {

    private String fullName;

    private String shortName;

    /**
     * Corresponde al Rut o DNI
     */
    private String identifier;

    /**
     * Listado de configuraciones
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GenericAttributeEntity> attributes = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<BillEntity> Invoices;

}
