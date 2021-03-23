package com.example.SpringBootExample.service;

import com.example.SpringBootExample.dao.UserDao;
import com.example.SpringBootExample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findById(Long id){
        return userDao.findById(id);
    }

    public List<User> findAll(){
        return userDao.findAll();
    }

    public User saveUser (User user){
        return userDao.saveUser(user);
    }

    public void deleteById (Long id){
        userDao.deleteById(id);
    }

    public User findByName( String username){
        return userDao.findByName(username);
    }

    public User update(User user){
        return userDao.update(user);
    }

}
