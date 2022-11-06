package com.bsi.passwordwalleet3.services;

import com.bsi.passwordwalleet3.entity.User;
import com.bsi.passwordwalleet3.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/users")
    public ResponseEntity getUser() throws JsonProcessingException {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(objectMapper.writeValueAsString(users));
    }
    @RequestMapping("/")
    public String hello(){
        return "Hello world";
    }
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody User user){
        Optional<User> userFromDb = userRepository.findByLogin(user.getLogin());

        if(userFromDb.isPresent()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        Optional<User> userFromDb = userRepository.findByLogin(user.getLogin());

        if(userFromDb.isEmpty() || wrongPassword(userFromDb, user)){
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

    public boolean wrongPassword(Optional<User> userFromDb, User user){
        return !userFromDb.get().getPasswordHash().equals(user.getPasswordHash());
    }
}
