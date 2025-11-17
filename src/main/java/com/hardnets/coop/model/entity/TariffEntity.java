package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;


/**
 * Its related to water meter and when this change the new value start on assign date
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @Enumerated(EnumType.STRING)
    private ClientTypeEnum clientType;

    /**
     * Tamaño en milímetros del medidor al que aplica la tarifa
     */
    @Enumerated(EnumType.STRING)
    private DiameterEnum diameter;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private StatusEnum status;

}
