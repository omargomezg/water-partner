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
     * Value of flat fee
     */
    @Column
    private Integer flatFee;

    /**
     * Value of cubic meter
     */
    @Column
    private BigDecimal cubicMeter;

    @LastModifiedDate
    private Instant lastUpdate;

    @ManyToOne
    @JoinColumn(name = "clienttype_id")
    private DropDownListEntity clientType;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private DropDownListEntity size;

}
