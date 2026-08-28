package com.bananas.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bananas.jwt.entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long>{

}
