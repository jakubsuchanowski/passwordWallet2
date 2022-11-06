package com.bsi.passwordwalleet3.services;


import com.bsi.passwordwalleet3.encryptionAlghoritms.AESenc;
import com.bsi.passwordwalleet3.entity.Password;
import com.bsi.passwordwalleet3.entity.User;
import com.bsi.passwordwalleet3.repository.PasswordRepository;
import com.bsi.passwordwalleet3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Optional;

@RestController
public class PasswordService {

   @Autowired
    PasswordRepository passwordRepository;

    @Autowired
    UserRepository userRepository;


//    @PostMapping("/passwords")
//    public ResponseEntity addPassword(@RequestHeader("login") String login, @RequestBody Password webPassword){
//        Optional<User> userFromDb = userRepository.findByLogin(login);
//
//        if (userFromDb.isEmpty()){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        Password password = new Password(null, webPassword.getPassword(), userFromDb.get(), webPassword.getWebAddress(), webPassword.getDescription(), webPassword.getLogin());
//        Password savedPassword = passwordRepository.save(password);
//        return ResponseEntity.ok(savedPassword);
//    }


public static AESenc aeSenc;

    @PostMapping("/passwords")
    public ResponseEntity addPassword(@RequestHeader("login") String login, @RequestBody Password webPassword) throws Exception {
        Optional<User> userFromDb = userRepository.findByLogin(login);
        if (userFromDb.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Key key = aeSenc.generateKey(userFromDb.get().getPasswordHash());

            Password password = new Password(null, aeSenc.encrypt(webPassword.getPassword(), key), userFromDb.get(), webPassword.getWebAddress(), webPassword.getDescription(), webPassword.getLogin());
            Password savedPassword = passwordRepository.save(password);
            return ResponseEntity.ok(savedPassword);

        }
//    public void addPassword(@RequestHeader("login") String login, @RequestBody Password webPassword) throws Exception {
//        Optional<User> userFromDb = userRepository.findByLogin(login);
//        if (userFromDb.isEmpty()) {
//        }
//             ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }


//    Key key = aeSenc.generateKey(password.getUser().getPasswordHash());
//        try {
//
//            Password password = new Password(null, aeSenc.encrypt(webPassword.getPassword(), key), userFromDb.get(), webPassword.getWebAddress(), webPassword.getDescription(), webPassword.getLogin());
//            passwordRepository.save(password);
//        }
//        catch (Exception e){
//
//        }
  }


