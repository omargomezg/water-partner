package com.hardnets.coop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controlador para las peticiones asociadas a un documento de tipo factura o boleta
 */
@Controller
@RestController
public class DocumentController {

    @GetMapping
    public ResponseEntity getDocument(@RequestParam Optional<Long> id) {
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity updateDocument() {
        return ResponseEntity.ok().build();
    }

}
