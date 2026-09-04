package com.bananas.jwt.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    /**
     * Correo o nombre de usuario del usuario registrado
     */
    private String user;

    /**
     * Contraseña del usuario registrado
     */
    private String password;

}