package com.hardnets.coop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
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
    private BigDecimal cubicMeter;

    /**
     * Ultima actualización de los montos
     */
    @LastModifiedDate
    private Instant lastUpdate;

    @ManyToOne
    @JoinColumn(name = "clienttype_id")
    private DropDownListEntity clientType;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private DropDownListEntity size;

}
