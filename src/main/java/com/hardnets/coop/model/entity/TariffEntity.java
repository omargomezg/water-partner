package com.hardnets.coop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;


/**
 * Its related to water meter and when this change the new value start on assign date
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "tariffs")
public class TariffEntity extends BaseEntity {

    /**
     * Cargo fijo
     */
    @Column
    private Integer flatFee;

    /**
     * Valor del m3
     */
    @Column
    private Float cubicMeter;

    /**
     * Ultima actualización de los montos
     */
    @LastModifiedDate
    private Instant lastUpdate;

    /**
     * Tipo de cliente
     */
    @ManyToOne
    @JoinColumn(name = "clienttype_id")
    private DropDownListEntity clientType;

    /**
     * Tamaño en milímetros del medidor al que aplica la tarifa
     */
    @ManyToOne
    @JoinColumn(name = "size_id")
    private DropDownListEntity size;

}
