package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.payment.PaymentByUserDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@AllArgsConstructor
@RequestMapping("/v1/user-payment")
@RestController
public class UserPaymentController {

    @GetMapping
    public ResponseEntity<List<PaymentByUserDto>>  getPayments() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping
    public ResponseEntity<List<PaymentByUserDto>>  postPayment() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PutMapping
    public ResponseEntity<List<PaymentByUserDto>>  putPayment() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @DeleteMapping
    public ResponseEntity<String> deletePayment() {
        return ResponseEntity.accepted().build();
    }
}
