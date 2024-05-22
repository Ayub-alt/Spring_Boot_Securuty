package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.hiber.model.User;
import ru.kata.spring.boot_security.demo.hiber.service.UserService;

import java.util.LinkedList;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userSv;

    @Autowired
    public UserController(UserService userSv){

        this.userSv = userSv;
    }

    @GetMapping("")
    public String getUserProfile(ModelMap model, Authentication authentication){
        User currentUser = userSv.getUserByName(authentication.getName());
        if(currentUser == null){
            throw new UsernameNotFoundException("The user is null!");
        }
        model.addAttribute("user", currentUser);
        return "user";
    }




}
