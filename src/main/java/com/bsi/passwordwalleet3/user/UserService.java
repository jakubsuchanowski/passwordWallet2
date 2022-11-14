package com.bsi.passwordwalleet3.user;

import com.bsi.passwordwalleet3.encryptionAlghoritms.AESenc;
import com.bsi.passwordwalleet3.encryptionAlghoritms.HMAC;
import com.bsi.passwordwalleet3.encryptionAlghoritms.SHA512;
import com.bsi.passwordwalleet3.password.Password;
import com.bsi.passwordwalleet3.password.PasswordService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Optional;

import static com.bsi.passwordwalleet3.encryptionAlghoritms.HMAC.calculateHMAC;

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

    public void changePassword(String login, String newPassword) throws Exception{
        Optional<User> userFromDb = userRepo.findByLogin(login);
        if(userFromDb.get().getIsPasswordKeptAsHash()){
            String newSalt = sha512.generateSalt();
            String sha = SHA512.calculateSHA512(newSalt + newPassword);
            Key key = aeSenc.generateKey(pepper);
            String newUserPasswordWithSha = aeSenc.encrypt(sha,key);
            userFromDb.get().setPasswordHash(newUserPasswordWithSha);
            userFromDb.get().setSalt(newSalt);
            userRepo.save(userFromDb.get());
        }

        if(!userFromDb.get().getIsPasswordKeptAsHash()) {
            String newSalt = sha512.generateSalt();
            String sha = SHA512.calculateSHA512(newSalt + newPassword);
            String newUserPasswordWithHMAC = calculateHMAC(sha,pepper);
            userFromDb.get().setPasswordHash(newUserPasswordWithHMAC);
            userFromDb.get().setSalt(newSalt);
            userRepo.save(userFromDb.get());
        }

    }

    public boolean wrongPassword(Optional<User> userFromDb, User user){
        return !userFromDb.get().getPasswordHash().equals(user.getPasswordHash());
    }
}
