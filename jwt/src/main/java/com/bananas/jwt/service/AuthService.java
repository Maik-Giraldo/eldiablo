package com.bananas.jwt.service;

import com.bananas.jwt.repository.RolesRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bananas.jwt.dto.GlobalMessageResponseDTO;
import com.bananas.jwt.dto.RegisterRequestDTO;
import com.bananas.jwt.dto.UserDTO;
import com.bananas.jwt.entity.Users;
import com.bananas.jwt.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;

    private final RolesRepository rolesRepository;

    public GlobalMessageResponseDTO<UserDTO> register(RegisterRequestDTO request) {
        GlobalMessageResponseDTO<UserDTO> response = new GlobalMessageResponseDTO<>();

        if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
            response.setMessage("El correo ya se encuentra en uso");
            return response;
        }

        if (rolesRepository.findById(request.getRolId()).isEmpty()) {
            response.setMessage("El rol no existe");
            return response;
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRolId(request.getRolId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        usersRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRolId(user.getRolId());

        response.setData(userDTO);
        response.setMessage("Usuario registrado correctamente");

        return response;
    }
}
