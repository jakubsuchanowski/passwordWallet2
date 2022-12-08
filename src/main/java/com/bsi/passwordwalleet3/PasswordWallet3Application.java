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
        String addHaslo="haslohaslo123";
        String haslo="test";
        String sha = SHA512.calculateSHA512("QWERT12345!@#"+haslo);
        Key key = AESenc.generateKey(pepper);
        String masterKey = AESenc.encrypt(sha,key);
        System.out.println("hasloSHA: "+masterKey);


        System.out.println("sha: "+sha);
        String hmac = calculateHMAC(sha, pepper);
        System.out.println("hasloHMAC: "+hmac);
        Key key2 = AESenc.generateKey(haslo);
        String encryptPassword = AESenc.encrypt(addHaslo,key2);
        String decryptPassword = AESenc.decrypt(encryptPassword,key2);
        System.out.println("key2: "+key2);
        System.out.println("AES zaszyfrowane: "+encryptPassword);
        System.out.println("AES odszyfrowane: "+decryptPassword);

        String tekst123 = "drugiTestCalculateSHA512qwerty";
        String key123 = "qwerty12345";
        String hmacToTest = calculateHMAC(tekst123, key123 );
        System.out.println("HMAC TO TEST= "+hmacToTest);
        byte[] md5test = AESenc.calculateMD5(tekst123);
        System.out.println("MD5 byte: "+ md5test);

        String sha12345 = SHA512.calculateSHA512(tekst123);
        System.out.println("SHA512 calculate is :"+sha12345);

    }
}
