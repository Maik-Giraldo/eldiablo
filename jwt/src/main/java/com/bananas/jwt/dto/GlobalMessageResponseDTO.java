package com.bananas.jwt.dto;

import lombok.Data;

@Data
public class GlobalMessageResponseDTO<T> {
    /**
     * Mensaje de respuesta
     */
    private String message;

    private T data;
}