package com.example.SpringBootExample.dao;

import com.example.SpringBootExample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao  {
    public User findById(Long id);

    public User findByName(String username);

    public List<User> findAll();

    public User saveUser(User user);

    public void deleteById(Long id);

    public User update(User myUser);

}
