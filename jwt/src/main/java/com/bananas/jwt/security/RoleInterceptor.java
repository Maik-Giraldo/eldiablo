package com.bananas.jwt.security;

import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // Verifica que el handler sea un método del controller y no otra cosa
        if (!(handler instanceof HandlerMethod method)) {
            return true; // Si no es un a instancia a un método no nos importa, y lo dejamos seguir su
                         // curso
        }

        // Busca la anotación @RequiresRole en el método
        RequiresRole annotation = method.getMethodAnnotation(RequiresRole.class);

        if (annotation == null) {
            annotation = method.getBeanType().getAnnotation(RequiresRole.class);
        }

        if (annotation == null) {
            return true; // Tampoco nos importa
        }

        Object rol = request.getAttribute("rolId");

        if (!(rol instanceof Long rolId)) {
            response.setContentType("application/json"); // Tipo de respuesta
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código 401 No autorizado
            response.getWriter().write("{\"error\": \"User is not authenticated\"}");
            return false; // Mochamos la petición antes de que llegue al controller
        }

        // Verifica si el rol del usuario esta dentro de los roles permitidos
        Boolean hasRole = Arrays.stream(annotation.value()).anyMatch(role -> role.getId() == rolId);

        if (!hasRole) {
            response.setContentType("application/json"); // Tipo de respuesta
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Código 403 Sin permisos
            response.getWriter().write("{\"error\": \"Not have permission to perform this action.\"}");
            return false; // Mochamos la petición antes de que llegue al controller
        }

        // Si pasa todas las validaciones, permite llegar al controller
        return true;
    }
}