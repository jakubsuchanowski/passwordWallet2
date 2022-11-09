package com.bsi.passwordwalleet3.user;

import com.bsi.passwordwalleet3.encryptionAlghoritms.AESenc;
import com.bsi.passwordwalleet3.encryptionAlghoritms.HMAC;
import com.bsi.passwordwalleet3.encryptionAlghoritms.SHA512;
import com.bsi.passwordwalleet3.password.PasswordService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    ObjectMapper objectMapper;



    private final PasswordService passwordService;
    private final AESenc aeSenc;
    private final SHA512 sha512;
    private final HMAC hmac;
    String pepper = "qwerty123456";


    public void addUser(User userDb) throws Exception {
        Optional<User> userFromDb = userRepo.findByLogin(userDb.getLogin());
        if(userFromDb.isPresent()){

        }

        if(userDb.getIsPasswordKeptAsHash()){
            registerWithSha512Hash(userDb);
        }
        if(!userDb.getIsPasswordKeptAsHash()){
            registerWithHmac(userDb);
        }
    }


    public void registerWithSha512Hash(User userDb) throws Exception {
        User user = sha512.encryptSha512(userDb, null);

        Key key = AESenc.generateKey(pepper);

        String password = user.getPasswordHash();
        user.setPasswordHash(aeSenc.encrypt(password,key));

        userRepo.save(user);
    }
    public void registerWithHmac(User userDb) throws Exception{
        User user = sha512.encryptSha512(userDb, null);

        user.setPasswordHash(hmac.calculateHMAC(user.getPasswordHash(),pepper));

        userRepo.save(user);
    }




    public void login(@RequestBody User user){
        Optional<User> userFromDb = userRepo.findByLogin(user.getLogin());

        if(userFromDb.isEmpty() || wrongPassword(userFromDb, user)){

        }
    }

    public boolean wrongPassword(Optional<User> userFromDb, User user){
        return !userFromDb.get().getPasswordHash().equals(user.getPasswordHash());
    }
}
