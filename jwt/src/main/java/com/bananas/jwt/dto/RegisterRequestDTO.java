package com.bananas.jwt.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    /**
     * nombre de usuario
     */
    private String username;

    /**
     * correo del usuario
     */
    private String email;

    /**
     * contraseña del usuario
     */
    private String password;

    /**
     * rol del usuario
     */
    private Long rolId;
}
