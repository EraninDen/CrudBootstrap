package com.example.SpringBootExample.service;

import com.example.SpringBootExample.dao.UserDao;
import com.example.SpringBootExample.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserDao userDao;

    public UserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.SpringBootExample.model.User user = userDao.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
            return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
        }
        return new User(user.getUsername(),user.getPassword(), grantedAuthorities);
    }
}
