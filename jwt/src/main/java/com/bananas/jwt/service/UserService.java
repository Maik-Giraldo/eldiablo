package com.bananas.jwt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bananas.jwt.dto.UserDTO;
import com.bananas.jwt.entity.Users;
import com.bananas.jwt.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    public List<UserDTO> findAll() {

        List<Users> users = usersRepository.findAll();
        List<UserDTO> response = new ArrayList<>();

        for (Users user : users) {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setRolId(user.getRolId());
            response.add(dto);
        }

        return response;
    }
}
