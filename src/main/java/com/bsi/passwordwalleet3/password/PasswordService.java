package com.bsi.passwordwalleet3.password;


import com.bsi.passwordwalleet3.encryptionAlghoritms.AESenc;
import com.bsi.passwordwalleet3.user.User;
import com.bsi.passwordwalleet3.password.Password;
import com.bsi.passwordwalleet3.password.PasswordRepo;
import com.bsi.passwordwalleet3.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.List;
import java.util.Optional;

@Service
public class PasswordService {

    @Autowired
    PasswordRepo passwordRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AESenc aeSenc;

    public void addPassword(String login, Password webPassword) throws Exception {
        Optional<User> userFromDb = userRepo.findByLogin(login);

        Key key = aeSenc.generateKey(userFromDb.get().getPasswordHash());

        Password password = new Password(null, aeSenc.encrypt(webPassword.getPassword(), key), userFromDb.get(), webPassword.getWebAddress(), webPassword.getDescription(), webPassword.getLogin());
        passwordRepo.save(password);
//        System.out.println(userFromDb.get().getPasswordHash());

    }

//    public void addPassword(Password password, User user)throws Exception{
//        Optional<User> userFromDb = userRepository.findByLogin(user.getLogin());
//        password.setUser(user);
//
//        raw = (String)
//        Key key = aeSenc.generateKey(user.getPasswordHash());
//
//        String newPassword = aeSenc.encrypt(password.getPassword(),)
//    }

    public List<Password> showPasswords(@RequestHeader("login") String userLogin) {
        Optional<User> userFromDb = userRepo.findByLogin(userLogin);

        return passwordRepo.findAllByUserId(userFromDb.get().getId());
    }




public String decryptPassword(Long passwordId, String userPassword) throws Exception{
//        passwordRepository.findById(passwordId).ifPresent(System.out::println);
            Optional<Password> passwordFromDb = passwordRepo.findById(passwordId);
        if(passwordFromDb.isEmpty()){
            throw new Exception("Password doesn't exception");
        }
//
            Password password = passwordFromDb.get();
            Key key = aeSenc.generateKey(userPassword);

            return aeSenc.decrypt(password.getPassword(), key);

    }

  }


