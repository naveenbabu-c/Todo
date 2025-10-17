package com.firstSpring.First.Spring.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstSpring.First.Spring.Models.User;
import com.firstSpring.First.Spring.Service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


   private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> create( @Valid @RequestBody User user) {
        try {
            userService.create(user);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody User user) {

        String result = userService.login(user);
        if (result.equals("invalid credentials")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", result));
        }

        return ResponseEntity.ok(Map.of("token", result));
    }

}
