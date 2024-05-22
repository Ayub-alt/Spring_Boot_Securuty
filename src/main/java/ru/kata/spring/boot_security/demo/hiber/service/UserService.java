package ru.kata.spring.boot_security.demo.hiber.service;


import ru.kata.spring.boot_security.demo.hiber.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    void deleteUser(long id);
    void updateUser(User user);
    User getUserById(long id);
    List<User> listUsers();
    public User getUserByName(String name);
}
