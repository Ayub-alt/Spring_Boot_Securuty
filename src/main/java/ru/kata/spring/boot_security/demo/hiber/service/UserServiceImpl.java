package ru.kata.spring.boot_security.demo.hiber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.hiber.model.Role;
import ru.kata.spring.boot_security.demo.hiber.model.User;
import ru.kata.spring.boot_security.demo.hiber.repository.UserRepository;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepository userRep;

    @Override
    public void addUser(User user) {
        userRep.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userRep.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        userRep.save(user);

    }

    @Override
    public User getUserById(long id) {
        return userRep.findById(id).orElse(null);
    }

    @Override
    public User getUserByName(String name){
        User user = userRep.getUserByName(name).orElse(null);
        if(user==null){
            throw new UsernameNotFoundException("The is not found");
        }
        return user;
    }

    @Override
    public List<User> listUsers() {
        return userRep.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRep.getUserByName(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("Username: "+ user.getName()+". Role: " + user.getRole().stream().iterator().toString());

        Set<Role> roles = user.getRole();
        List<String> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add((role.getName()));
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(authorities.stream().toArray(String[]::new))
                .build();

    }
}


