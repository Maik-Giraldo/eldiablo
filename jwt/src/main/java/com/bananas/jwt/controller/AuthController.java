package com.bananas.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bananas.jwt.dto.GlobalMessageResponseDTO;
import com.bananas.jwt.dto.RegisterRequestDTO;
import com.bananas.jwt.dto.UserDTO;
import com.bananas.jwt.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    /**
     * Servicio de autenticacion
     */
    private final AuthService authService;

    /**
     * Método para el registro de usuarios
     * 
     * @param request
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<GlobalMessageResponseDTO<UserDTO>> register(@RequestBody RegisterRequestDTO request) {
        try {
            GlobalMessageResponseDTO<UserDTO> response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
