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
        String haslo="zosia";
        String sha = SHA512.calculateSHA512("26d77be7-daea-4a48-90a9-3f05880cfc4a"+haslo);
        Key key = AESenc.generateKey(pepper);
        String masterKey = AESenc.encrypt(haslo,key);
        System.out.println("hasloSHA: "+sha);

        String hmac = calculateHMAC(haslo, pepper);
        System.out.println("hasloHMAC: "+hmac);
        String userPassword = sha;
        Key key2 = AESenc.generateKey(masterKey);
        String encryptPassword = AESenc.encrypt(addHaslo,key2);
        String decryptPassword = AESenc.decrypt(encryptPassword,key2);
        System.out.println("AES zaszyfrowane: "+encryptPassword);
        System.out.println("AES odszyfrowane: "+decryptPassword);



    }
    public void passwordHash2(String encryptPassword, String decryptPassword) throws Exception{




    }


}
