package com.hardnets.coop.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import com.hardnets.coop.model.dto.CreateUserDto;
import com.hardnets.coop.model.dto.UserDto;
import com.hardnets.coop.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Collection<UserDto>> getUsers(@RequestParam(required = false) String rut) {
        if (rut == null)
            return ResponseEntity.ok(userService.getUsers());
        return ResponseEntity.ok(new HashSet<>(List.of(userService.getByRut(rut))));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserDto user) {
        log.info("access to Post User");
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestParam String rut, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(userDto));
    }

}
