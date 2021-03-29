package com.example.SpringBootExample.dao;

import com.example.SpringBootExample.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<Role, Long> {

    Role findByRole(String role);
}
