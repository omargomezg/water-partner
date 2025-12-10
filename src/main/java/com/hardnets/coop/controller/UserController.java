package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.CreateUserDto;
import com.hardnets.coop.model.dto.PageResponse;
import com.hardnets.coop.model.dto.UserDTO;
import com.hardnets.coop.model.dto.UserFilterRequest;
import com.hardnets.coop.service.impl.UserDetailServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@AllArgsConstructor
@RequestMapping("/v1/user")
@RestController
public class UserController {

    private final UserDetailServiceImpl userDetailService;

    @GetMapping
    public ResponseEntity<PageResponse<UserDTO>> getUsers(UserFilterRequest params) {
        PageResponse<UserDTO> response = new PageResponse<>();
        var users = userDetailService.findAll(params);
        List<UserDTO> userDtos = users.stream().map(UserDTO::new).toList();
        response.setContent(userDtos);
        response.setTotalElements(userDetailService.count(params));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid CreateUserDto user) {
        var exists =  userDetailService.count(UserFilterRequest.builder().email(user.getEmail()).build());
        if(exists > 0) {
           return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(userDetailService.create(user), HttpStatus.CREATED);
    }

    @PutMapping("/{dni}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String dni, @RequestBody UserDTO userDto) {
        return ResponseEntity.ok(userDetailService.update(userDto));
    }

    @PutMapping("{dni}/password")
    public ResponseEntity<?> updatePassword(@PathVariable String dni, @RequestBody String password) {
        userDetailService.updatePassword(dni, password);
        return ResponseEntity.noContent().build();
    }

}
