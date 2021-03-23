package com.example.SpringBootExample.controller;

import com.example.SpringBootExample.model.User;
import com.example.SpringBootExample.service.RoleService;
import com.example.SpringBootExample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

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
        model.addAttribute("username", name);

        return "/admin";
    }

    @GetMapping("/user")
    public String getUser(@RequestParam(required = false) String username,Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("username", name);
        model.addAttribute("user", userService.findByName(name));
        return "user";
    }

    @GetMapping("/admin/users")
    public String findAll(@RequestParam(required = false) String username, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("username", name);
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/admin/user-create")
    public String createUserForm(User user, Model model){
        model.addAttribute("new_user", new User());
        return "user-create";
    }

    @PostMapping("admin/user-create")
    public String createUser(@ModelAttribute("new_user") User user){
        user.setRoles(Collections.singleton(roleService.findById(1L)));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/admin/user-update")
    public String updateUser(@ModelAttribute("user") User user, HttpServletRequest request){
        user.setRoles(Collections.singleton(roleService.findById(Long.valueOf(request.getParameter("role")))));
        userService.update(user);
        return "redirect:/admin/users";
    }

}
