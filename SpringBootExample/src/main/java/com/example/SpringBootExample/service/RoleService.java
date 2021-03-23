package com.example.SpringBootExample.service;

import com.example.SpringBootExample.dao.RoleDao;
import com.example.SpringBootExample.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role findById(Long id){
        return roleDao.getOne(id);
    }
}
