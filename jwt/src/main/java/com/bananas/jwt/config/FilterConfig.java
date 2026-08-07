package com.bananas.jwt.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bananas.jwt.filter.JwtValidationFilter;

@Configuration // Fábrica de beans
// Springboot la lee al arrancar la app para configurar el comportamiento de esta misma
public class FilterConfig {
    @Bean
    FilterRegistrationBean<JwtValidationFilter> jwtFilter(JwtValidationFilter jwtValidationFilter) {
        // Creamos un contenedor de registro del bean para el filtro
        FilterRegistrationBean<JwtValidationFilter> registrationBean = new FilterRegistrationBean<>();

        // es decirle a spring que este es el filtro con el que quiero que trabaje
        registrationBean.setFilter(jwtValidationFilter);

        // Definir el alcance de este filtro, quiero que revise todas las peticiones que entren a mi server
        registrationBean.addUrlPatterns("/*");

        // Establecemos la prioridad de ejecución de los filtros, este se va a ejecutar antes que todos los filtros internos
        registrationBean.setOrder(1);

        // Retornamos el registro configurado para que spring lo guarde en su contexto
        return registrationBean;
    }
}
