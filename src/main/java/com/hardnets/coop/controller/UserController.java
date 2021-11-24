package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.CreateUserDto;
import com.hardnets.coop.model.dto.UserDto;
import com.hardnets.coop.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user) {
        log.info("access to Post User");
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestParam String rut, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(userDto));
    }

}
