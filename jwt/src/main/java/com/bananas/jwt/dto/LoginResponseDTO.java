package com.bananas.jwt.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    /**
     * Jwt del usuario logueado
     */
    private String jwt;
}
