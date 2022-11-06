//package com.bsi.passwordwalleet3;
//
//import com.bsi.passwordwalleet3.entity.User;
//import com.bsi.passwordwalleet3.services.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequiredArgsConstructor
//public class AppController {
//    private final UserService userService;
//    @PostMapping("/passwords")
//    public ResponseEntity<?> addPassword(@RequestBody User user){
//
//        try{
//            userService.
//        }
//        catch (UserLogInException e)
//        {
//
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//        return ResponseEntity.ok().build();
//    }
//
//}
