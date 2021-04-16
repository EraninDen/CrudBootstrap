package com.example.SpringBootExample.controller;

import com.example.SpringBootExample.model.Role;
import com.example.SpringBootExample.model.User;
import com.example.SpringBootExample.service.RoleService;
import com.example.SpringBootExample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String hello(){
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String getAdmin(@RequestParam(required = false) String username, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        boolean hasAdmin;
        if(roles.contains("ROLE_ADMIN")){
            hasAdmin=true;
        } else {
            hasAdmin=false;
        }
        model.addAttribute("hasAdmin", hasAdmin);
        model.addAttribute("username", name);
        model.addAttribute("user", userService.findByName(name));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("new_user", new User());
        return "/admin";
    }

    @GetMapping("/user")
    public String getUser(@RequestParam(required = false) String username,Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        boolean hasAdmin;
        if(roles.contains("ROLE_ADMIN")){
            hasAdmin=true;
        } else {
            hasAdmin=false;
        }
        model.addAttribute("hasAdmin", hasAdmin);
        model.addAttribute("username", name);
        model.addAttribute("user", userService.findByName(name));
        return "user";
    }

    @PostMapping("admin/user-create")
    public String createUser(@ModelAttribute("new_user") User user, HttpServletRequest request){
        user.setRoles(Collections.singleton(roleService.findById(Long.valueOf(request.getParameter("role")))));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/user-update")
    public String updateUser(@ModelAttribute("user") User user, HttpServletRequest request){
        user.setRoles(Collections.singleton(roleService.findById(Long.valueOf(request.getParameter("role")))));
        userService.update(user);
        return "redirect:/admin";
    }

}
