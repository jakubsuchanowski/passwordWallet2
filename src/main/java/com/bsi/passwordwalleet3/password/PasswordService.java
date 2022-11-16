package com.bsi.passwordwalleet3.password;


import com.bsi.passwordwalleet3.encryptionAlghoritms.AESenc;
import com.bsi.passwordwalleet3.encryptionAlghoritms.SHA512;
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

import static com.bsi.passwordwalleet3.encryptionAlghoritms.HMAC.calculateHMAC;

@Service
public class PasswordService {

    @Autowired
    PasswordRepo passwordRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AESenc aeSenc;
    String pepper = "qwerty123456";


    public void addPassword(String login, Password webPassword) throws Exception {
        Optional<User> userFromDb = userRepo.findByLogin(login);

        Key key = aeSenc.generateKey(userFromDb.get().getPasswordHash());

        Password password = new Password(null, aeSenc.encrypt(webPassword.getPassword(), key), userFromDb.get(), webPassword.getWebAddress(), webPassword.getDescription(), webPassword.getLogin());
        passwordRepo.save(password);
//        System.out.println(userFromDb.get().getPasswordHash());

    }


    public List<Password> showPasswords(String userLogin) {
        Optional<User> userFromDb = userRepo.findByLogin(userLogin);

        return passwordRepo.findAllByUserId(userFromDb.get().getId());
    }




public String decryptPassword(Long passwordId, String userPassword) throws Exception{
        Optional<Password> passwordFromDb = passwordRepo.findById(passwordId);

        String decryptedPassword;
        if(!passwordFromDb.get().getUser().getIsPasswordKeptAsHash()){
            String sha = SHA512.calculateSHA512(passwordFromDb.get().getUser().getSalt() + userPassword);
            String userPasswordWithHmac = calculateHMAC(sha, pepper);
            Password password = passwordFromDb.get();
            Key key = aeSenc.generateKey(userPasswordWithHmac);
            decryptedPassword =  aeSenc.decrypt(password.getPassword(), key);
            return decryptedPassword;
        }

        if(passwordFromDb.get().getUser().getIsPasswordKeptAsHash()){
            String sha = SHA512.calculateSHA512(passwordFromDb.get().getUser().getSalt() + userPassword);
            Key key2 =aeSenc.generateKey(pepper);
            String userPasswordWithSha = aeSenc.encrypt(sha,key2);
            Key key = aeSenc.generateKey(userPasswordWithSha);
            Password password = passwordFromDb.get();

            decryptedPassword = aeSenc.decrypt(password.getPassword(), key);
            return decryptedPassword;
    }
    return "Błąd";
    }
    public void changeUserPasswords(String login, String oldUserPassword) throws Exception {
        Optional<User> userFromDb = userRepo.findByLogin(login);
       List<Password> passwordsList = passwordRepo.findAllByUserId(userFromDb.get().getId());
        for(int i=0; i<passwordsList.size();i++) {
            Password password = passwordsList.get(i);
            System.out.println(password.getPassword());
            Key key = aeSenc.generateKey(oldUserPassword);
            String decryptedPassword = aeSenc.decrypt(password.getPassword(),key);
            Key key2 = aeSenc.generateKey(userFromDb.get().getPasswordHash());

            String encryptedPassword = aeSenc.encrypt(decryptedPassword,key2);

            password.setPassword(encryptedPassword);

            passwordRepo.save(password);
            }
        }
    }


