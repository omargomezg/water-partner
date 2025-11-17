package com.hardnets.coop.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.hardnets.coop.model.dto.CreateUserDto;
import com.hardnets.coop.model.dto.UserDto;
import com.hardnets.coop.service.impl.UserDetailServiceImpl;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@RequestMapping("/v1/user")
@RestController
public class UserController {

    private final UserDetailServiceImpl userDetailService;

    @GetMapping
    public ResponseEntity<Collection<UserDto>> getUsers(@RequestParam(required = false) String dni) {
        if (dni == null)
            return ResponseEntity.ok(userDetailService.getUsers());
        return ResponseEntity.ok(new HashSet<>(List.of(userDetailService.getByDni(dni))));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserDto user) {
        log.info("access to Post User");
        return new ResponseEntity<>(userDetailService.create(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestParam String dni, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userDetailService.update(userDto));
    }

    @PutMapping("{dni}/password")
    public ResponseEntity<?> updatePassword(@PathVariable String dni, @RequestBody String password) {
        userDetailService.updatePassword(dni, password);
        return ResponseEntity.noContent().build();
    }

}
