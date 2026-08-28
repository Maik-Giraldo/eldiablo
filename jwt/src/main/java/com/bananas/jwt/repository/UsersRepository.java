package com.bananas.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bananas.jwt.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{
    Optional<Users> findByEmail(String email);
}
