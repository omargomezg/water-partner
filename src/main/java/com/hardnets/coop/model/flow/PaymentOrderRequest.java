package com.hardnets.coop.model.flow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentOrderRequest {
    /**
     * apiKey del comercio
     */
    private String apiKey;

    /**
     * Orden del comercio
     */
    private String commerceOrder;

    /**
     * Descripción de la orden
     */
    private String subject;

    /**
     * Moneda de la orden
     */
    private String currency;

    /**
     * Monto de la orden
     */
    private Long amount;

    /**
     * email del pagador
     */
    private String email;

    /**
     * Identificador del medio de pago. Si se envía el identificador, el pagador será redireccionado directamente al
     * medio de pago que se indique, de lo contrario Flow le presentará una página para seleccionarlo.
     * El medio de pago debe haber sido previamente contratado.
     * Podrá ver los identificadores de sus medios de pago en la sección "Mis Datos" ingresando a Flow con sus credenciales.
     * Para indicar todos los medios de pago utilice el identificador: 9
     */
    private Long paymentMethod;

    /**
     * url callback del comercio donde Flow confirmará el pago
     */
    private String urlConfirmation;

    /**
     * url de retorno del comercio donde Flow redirigirá al pagador
     */
    private String urlReturn;
    /**
     * Datos opcionales en formato JSON clave = valor
     * por ejemplo: {"rut":"9999999-9","nombre":"cliente 1"}
     */
    private String optional;

    /**
     * Tiempo en segundos para que una orden expire después de haber sido creada.
     * Si no se envía este parámetro la orden no expirará y estará vigente para pago por tiempo indefinido.
     * Si envía un valor en segundos, la orden expirará x segundos después de haber sido creada y no podrá pagarse.
     */
    private Integer timeout;

    /**
     * Id de comercio asociado. Solo aplica si usted es comercio integrador.
     */
    private String merchantId;

    /**
     * Moneda en que se espera se pague la orden
     */
    @JsonProperty(value = "payment_currency")
    private String paymentCurrency;

    /**
     * la firma de los parámetros efectuada con su secretKey
     */
    @JsonProperty(value = "s")
    private String signature;


    public PaymentOrderRequest() {
        this.apiKey = "5B656F4B-448C-42CB-98B5-8E7CDB96L7DA";
        this.paymentCurrency = "CLP";
        this.currency = "CLP";
    }

}
