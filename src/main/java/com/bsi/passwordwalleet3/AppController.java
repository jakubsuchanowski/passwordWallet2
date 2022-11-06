package com.bsi.passwordwalleet3;

import com.bsi.passwordwalleet3.entity.Password;
import com.bsi.passwordwalleet3.entity.User;
import com.bsi.passwordwalleet3.services.PasswordService;
import com.bsi.passwordwalleet3.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.System.out;


@RestController
@RequiredArgsConstructor
public class AppController {
    private final UserService userService;
    private final PasswordService passwordService;
//    @PostMapping("/passwords")
//    public ResponseEntity<?> addPassword(@RequestHeader("login") String login, @RequestBody Password webPassword){
//
//        try{
//            passwordService.addPassword(login,webPassword);
//        }
//        catch (Exception e)
//        {
//            out.println("Bład");
//        }
//
//        return ResponseEntity.ok().build();
//    }

}
