package com.bananas.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bananas.jwt.dto.GlobalMessageResponseDTO;
import com.bananas.jwt.dto.LoginRequestDTO;
import com.bananas.jwt.dto.LoginResponseDTO;
import com.bananas.jwt.dto.RegisterRequestDTO;
import com.bananas.jwt.dto.UserDTO;
import com.bananas.jwt.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/login")
    public ResponseEntity<GlobalMessageResponseDTO<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request) {
        try {
            GlobalMessageResponseDTO<LoginResponseDTO> response = authService.login(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("refreshToken")
    public ResponseEntity<GlobalMessageResponseDTO<LoginResponseDTO>> refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        GlobalMessageResponseDTO<LoginResponseDTO> response = new GlobalMessageResponseDTO<>();

        // Validamos si el header viene en la petición y si es legal
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setMessage("Token is invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String token = authHeader.replace("Bearer ", "");

        try {
            response = authService.refreshToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Token is invalid or expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
