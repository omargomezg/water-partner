package com.hardnets.coop.model.dto.municipal

import java.util.*

/**
 * Decreto que apruba el pago del subsidiado
 * @param number Número de decreto municipal que aprueba el subsidio => Código: NUM-DEC
 * @param approvalDate Fecha de aprobación del decreto => Código: FEC-DEC
 */
data class Decree(val number: Long?, val approvalDate: Date?)