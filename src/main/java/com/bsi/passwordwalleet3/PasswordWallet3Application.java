package com.bsi.passwordwalleet3;

import com.bsi.passwordwalleet3.encryptionAlghoritms.AESenc;
import com.bsi.passwordwalleet3.encryptionAlghoritms.SHA512;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Key;

import static com.bsi.passwordwalleet3.encryptionAlghoritms.HMAC.calculateHMAC;


@SpringBootApplication
public class PasswordWallet3Application {
    @Autowired
    AESenc aeSenc;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PasswordWallet3Application.class, args);
        String pepper = "qwerty123456";
        String addHaslo="zosia1";
        String haslo="zosia7";
        String sha = SHA512.calculateSHA512("4eb7b62b-4f2f-494f-845e-37d1d7754fc7"+haslo);
        Key key = AESenc.generateKey(pepper);
        String masterKey = AESenc.encrypt(sha,key);
        System.out.println("hasloSHA: "+masterKey);


        String hmac = calculateHMAC(sha, pepper);
        System.out.println("hasloHMAC: "+hmac);
        Key key2 = AESenc.generateKey(masterKey);
        String encryptPassword = AESenc.encrypt(addHaslo,key2);
        String decryptPassword = AESenc.decrypt(encryptPassword,key2);
        System.out.println("AES zaszyfrowane: "+encryptPassword);
        System.out.println("AES odszyfrowane: "+decryptPassword);



    }
}
