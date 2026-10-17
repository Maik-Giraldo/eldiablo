package com.bananas.jwt.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bananas.jwt.enums.RoleEnum;

// Define donde se puede usar la anotación
@Target(ElementType.METHOD)
// Está disponible en tiempo de ejecución (cuando el proyecto esté corriendo)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRole {
    RoleEnum[] value();
}