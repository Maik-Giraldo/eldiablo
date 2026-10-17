package com.bananas.jwt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bananas.jwt.dto.UserDTO;
import com.bananas.jwt.enums.RoleEnum;
import com.bananas.jwt.security.RequiresRole;
import com.bananas.jwt.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-users")
    @RequiresRole({RoleEnum.ADMIN, RoleEnum.USER})
    public ResponseEntity<List<UserDTO>> findAll() {
        try {
            List<UserDTO> response = userService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
