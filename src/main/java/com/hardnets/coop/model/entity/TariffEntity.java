package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;


/**
 * It's related to water meter and when this change the new value start on assign date
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
     * Indica a que tipo de cliente aplica esta tarifa
     */
    @ManyToOne
    @JoinColumn(name = "client_type_id", nullable = false, referencedColumnName = "id")
    private ClientTypeEntity clientType;

    /**
     * Tamaño en milímetros del medidor al que aplica la tarifa
     */
    @Enumerated(EnumType.STRING)
    private DiameterEnum diameter;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private StatusEnum status;

}
