package com.bsi.passwordwalleet3;

import com.bsi.passwordwalleet3.encryptionAlghoritms.AESenc;
import com.bsi.passwordwalleet3.encryptionAlghoritms.SHA512;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Key;

import static com.bsi.passwordwalleet3.encryptionAlghoritms.HMAC.calculateHMAC;


@SpringBootApplication
public class PasswordWallet3Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PasswordWallet3Application.class, args);

        String haslo="kamil123";
        System.out.println("hasloSHA: "+SHA512.calculateSHA512(haslo));
        String pepper = "12321312";
        String hmac = calculateHMAC(haslo, pepper);
        System.out.println("hasloHMAC: "+hmac);
        String userPassword = "kamil1";
        Key key = AESenc.generateKey(userPassword);
        String encryptPassword = AESenc.encrypt(haslo,key);
        String decryptPassword = AESenc.decrypt(encryptPassword,key);
        System.out.println("AES zaszyfrowane: "+encryptPassword);
        System.out.println("AES odszyfrowane: "+decryptPassword);


    }
    public void passwordHash2(String encryptPassword, String decryptPassword) throws Exception{




    }


}
