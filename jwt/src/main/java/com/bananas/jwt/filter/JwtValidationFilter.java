package com.bananas.jwt.filter;

import com.bananas.jwt.service.JwtService;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component // bean huerfano
@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        // Obtención del header Authorization
        String authHeader = request.getHeader("Authorization");

        // Validamos si el header viene en la petición y si es legal
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setContentType("application/json"); // Tipo de respuesta
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código 401 No autorizado
            response.getWriter().write("{\"error\": \"Header authorization is missing in the request\"}");
            return; // Mochamos la petición para que no llegue al controller
        }

        // Extraemos el token limpio
        String token = authHeader.replace("Bearer ", "");

        try {
            // Validamos contra el servicio jwt si el token es válido
            if (jwtService.isTokenValid(token)) {
                // Extraemos los claims con el jwtService
                String email = jwtService.extractSubject(token);
                Long rolId = jwtService.extractRolId(token);
                Long userId = jwtService.extractUserId(token);

                // Seteamos esos claims como atributos en la petición
                request.setAttribute("email", email);
                request.setAttribute("rolId", rolId);
                request.setAttribute("userId", userId);

                // Si todo está bien, continuamos al siguiente paso, puede ser otro filtro o
                // directamente al controlador
                filterChain.doFilter(request, response);
            } else {
                response.setContentType("application/json"); // Tipo de respuesta
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código 401 No autorizado
                response.getWriter().write("{\"error\": \"Token is invalid\"}");
            }
        } catch (Exception e) {
            response.setContentType("application/json"); // Tipo de respuesta
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código 401 No autorizado
            response.getWriter().write("{\"error\": \"Validation failed\"}");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.equals("/api/v1/register") || 
                path.equals("/api/v1/login") || 
                path.equals("/api/v1/refreshToken");
    }

}
