package com.bananas.jwt.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
public enum RoleEnum {
    ADMIN(1L),
    USER(2L),
    SALES(3L);

    private final Long id;

    public Long getId() {
        return id;
    }
}