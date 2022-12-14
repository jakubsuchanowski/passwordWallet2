package com.bsi.passwordwalleet3;

import com.bsi.passwordwalleet3.exception.UserLoginException;
import com.bsi.passwordwalleet3.password.Password;
import com.bsi.passwordwalleet3.password.PasswordRepo;
import com.bsi.passwordwalleet3.user.User;
import com.bsi.passwordwalleet3.models.CryptResponse;
import com.bsi.passwordwalleet3.user.UserRepo;
import com.bsi.passwordwalleet3.password.PasswordService;
import com.bsi.passwordwalleet3.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.System.out;


@RestController
@RequiredArgsConstructor
public class AppController {
    private final UserService userService;
    private final PasswordService passwordService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordRepo passwordRepo;



    @GetMapping("/users")
    public ResponseEntity getUser(User user) {
        try {

            List<User> users = (List<User>) userRepo.findAll();
            return ResponseEntity.ok(objectMapper.writeValueAsString(users));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();

        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User userDb) {
        try {
            userService.addUser(userDb);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        try {
            userService.login(user);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping("/addPassword")
    public ResponseEntity addPassword(@RequestHeader("login") String login, @RequestBody Password password){

        try {
            passwordService.addPassword(login,password);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/showPassword")
    public ResponseEntity showPasswords(@RequestHeader("login") String userLogin) {
        try {
            passwordService.showPasswords(userLogin);
        } catch (Exception e) {
            out.println("B??ad");
        }
        return ResponseEntity.ok(passwordService.showPasswords(userLogin));
    }

    @PostMapping("/encrypt")
    public ResponseEntity<CryptResponse> encryptPassword(@RequestParam Long passwordId){
        try {
            String encryptedPassword = passwordService.encryptPassword(passwordId);
            CryptResponse response = new CryptResponse(encryptedPassword);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<CryptResponse> decryptPassword(@RequestParam Long passwordId, @RequestBody User userPassword) throws Exception {
        try {
            String decryptedPassword = passwordService.decryptPassword(passwordId,userPassword);
            CryptResponse response = new CryptResponse(decryptedPassword);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/changePassword")
    public ResponseEntity changeUserPassword(@RequestHeader("login") String login,
                                             @RequestBody Password newPassword){
        try {
                userService.changePassword(login,newPassword);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}

