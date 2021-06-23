package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.CreateUserDto;
import com.hardnets.coop.model.dto.UserDto;
import com.hardnets.coop.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@Log4j2
@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/v1/user")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/v1/user/{rut}")
    public ResponseEntity<UserDto> getUsers(@PathVariable String rut) {
        return ResponseEntity.ok(userService.getByRut(rut));
    }

    @PostMapping("/v1/user")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserDto user) throws Exception {
        log.info("access to Post User");
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @PutMapping("/v1/user")
    public ResponseEntity<?> updateUser() {
        return ResponseEntity.ok("");
    }

}
