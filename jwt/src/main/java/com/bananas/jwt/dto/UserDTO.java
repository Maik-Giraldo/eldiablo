package com.bananas.jwt.dto;

import lombok.Data;

@Data
public class UserDTO {
    /**
     * Id del usuario
     */
    private Long id;

    /**
     * nombre de usuario
     */
    private String username;

    /**
     * correo del usuario
     */
    private String email;

    /**
     * rol del usuario
     */
    private Long rolId;
}
