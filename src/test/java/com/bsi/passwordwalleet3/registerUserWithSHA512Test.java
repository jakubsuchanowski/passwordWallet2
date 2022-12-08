package com.bsi.passwordwalleet3;

import com.bsi.passwordwalleet3.encryptionAlghoritms.AESenc;
import com.bsi.passwordwalleet3.encryptionAlghoritms.SHA512;

import com.bsi.passwordwalleet3.user.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class registerUserWithSHA512Test {

   private SHA512 sha512;
   private AESenc aeSenc;


    private String salt;
    private String pepper;
    private User entryData;
    private User expResult;

    @Before
    public void init() {
        sha512 = new SHA512();
        salt = "QWERT12345!@#";
        pepper= "qwerty123456";
        entryData = new User("test","test");

        expResult = new User(0L,"test","2LkQCO/GkejEvZeWrW4eXvtNaajbKBimJFGtwF7KlWMXLMY03AH9jZ2zk/RCFbA8MllFeFBvdTpmF6NkYqMpfEew1SVA1YVoEpf1/q8n6Uw5UZllkY+BZCQGYJERlk5ZC5VEbCWJdP/COPTut8xNinQGG2rEovSYVewcJb3+9X/+/fn1sZQTWwctS1CU2igT",salt,false);
    }

    @Test
    public void registerUserWithSha512Test_expectedUserHashData_whenSaltUserProvider() throws Exception{
        User newUser = sha512.encryptSha512(entryData, salt);
        Key key = AESenc.generateKey(pepper);

        String password = newUser.getPasswordHash();
        newUser.setPasswordHash(aeSenc.encrypt(password,key));
        assertEquals(expResult.getLogin(),newUser.getLogin());
        assertEquals(expResult.getPasswordHash(),newUser.getPasswordHash());

    }



}
