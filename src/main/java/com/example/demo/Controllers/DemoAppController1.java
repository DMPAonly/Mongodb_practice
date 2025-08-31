package com.example.demo.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class DemoAppController1 {
    private final UserRepository userRepository;

    public DemoAppController1(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @PostMapping("/createUser")
    public User creatUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getUser/{email}")
    public User getUser(@PathVariable String email) {
        return userRepository.findByEmail(email);
    }
}

