package com.hardnets.coop.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardnets.coop.exception.PaymentNotFoundException;
import com.hardnets.coop.model.dto.response.PendingPaymentDto;
import com.hardnets.coop.model.entity.PaymentEntity;
import com.hardnets.coop.model.flow.PaymentOrderResponse;
import com.hardnets.coop.model.flow.PaymentOrderStatusResponse;
import com.hardnets.coop.repository.PaymentRepository;
import com.hardnets.coop.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@Log4j2
@Service
public class FlowServiceImpl implements PaymentService {

    private static final Byte PENDING_PAY = 1;

    @Autowired
    PaymentRepository paymentRepository;

    @Value("${flow.secretKeyForSha256HMAC}")
    private String secretKeyForSha256;

    //TODO obtener desde BD
    @Value("${flow.key}")
    private String flowApiKey;

    //TODO obtener desde BD
    @Value("${flow.token}")
    private String flowToken;
    @Value("${app.url}")
    private String appUrl;
    @Value("${flow.url}")
    private String flowUrl;
    @Value("${front.url}")
    private String frontUrl;

    @Override
    public PaymentOrderResponse sendPaymentOrder(PendingPaymentDto paymentOrder) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        // adding headers to the api
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("apiKey", flowApiKey);
        body.add("subject", "Pago testing");
        body.add("currency", "CLP");
        body.add("amount", "2500");
        body.add("email", "omar.fdo.gomez@gmail.com");
        body.add("commerceOrder", Calendar.getInstance().toString().substring(0, 45));
        body.add("urlConfirmation", String.format("%s/api/v1/public/confirmation-payment-order", appUrl));
        body.add("urlReturn", String.format("%s/api/v1/public/url-return", appUrl));
        String signature = getSignature(body);
        body.add("s", signature);
        log.info("The signature: {}", signature);
        var restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(
                String.format("%s/api/payment/create?token=%s}", flowUrl, flowToken),
                new HttpEntity<>(body, headers),
                String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        var payment = objectMapper.readValue(result, PaymentOrderResponse.class);
        save(payment.getToken(), payment.getFlowOrder());
        return payment;
    }

    @Override
    public void save(String token, Long flowOrder) {
        PaymentEntity entity = new PaymentEntity();
        entity.setStatus(PENDING_PAY);
        entity.setToken(token);
        entity.setFlowOrder(flowOrder);
        paymentRepository.save(entity);
    }

    @Override
    public PaymentEntity findByToken(String token) {
        return paymentRepository.findByToken(token).orElseThrow(
                () -> new PaymentNotFoundException(token)
        );
    }

    @Override
    public PaymentEntity update(String token) {
        return null;
    }

    @Override
    public String confirmationPaymentOrder(String token, Byte status) {
        PaymentEntity payment = findByToken(token);
        payment.setStatus(status);
        paymentRepository.save(payment);
        return String.format("%s/payment/confirmation", frontUrl);
    }

    @Override
    public PaymentOrderStatusResponse findFlowPaymentStatusByToken(String token) throws InvalidKeyException, NoSuchAlgorithmException, JsonProcessingException {
        PaymentEntity payment = findByToken(token);
        var headers = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("apiKey", flowApiKey);
        body.add("token", token);
        String signature = getSignature(body);
        String url = String.format("%s/api/payment/getStatus?apiKey=%s&token=%s&s=%s", flowUrl, flowApiKey, token, signature);
        var restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        var paymentStatus = objectMapper.readValue(result, PaymentOrderStatusResponse.class);
        confirmationPaymentOrder(token, paymentStatus.getStatus());
        return paymentStatus;
    }

    /**
     * Firma un conjunto de datos
     *
     * @param body Un Map con los datos a firmar
     * @return Una firma en string
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private String getSignature(MultiValueMap<String, String> body) throws NoSuchAlgorithmException, InvalidKeyException {
        var stringBuilder = new StringBuilder();
        List<String> keyList = new ArrayList<>(body.keySet());
        Collections.sort(keyList);
        for (String key : keyList) {
            stringBuilder
                    .append(key)
                    .append(body.get(key).get(0));
        }
        var sha256HMAC = Mac.getInstance("HmacSHA256");
        var secretKey = new SecretKeySpec(secretKeyForSha256.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256HMAC.init(secretKey);
        log.info("{}", stringBuilder.toString());
        return byteArrayToHex(sha256HMAC.doFinal(stringBuilder.toString().getBytes()));
    }

    /**
     * Convierte un Byte[] a string
     *
     * @param a valor a convertir
     * @return un string que representa al valor del byte[]
     */
    private String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
