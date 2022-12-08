package com.bsi.passwordwalleet3;

import com.bsi.passwordwalleet3.encryptionAlghoritms.AESenc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Key;

import static org.junit.Assert.assertEquals;


@RunWith(JUnitParamsRunner.class)
public class AESencTest {


    @Autowired
    AESenc aeSenc;
    @Autowired
    Key key;
    public static final String keyValue = "klucz";

    @Before
    public void setUp() throws Exception {
            aeSenc = new AESenc();
            key = aeSenc.generateKey(keyValue);
    }


    @Test
    @Parameters({"haslo, GE9juaAvnMzJuTGrcLQfRA==","nowehaslo123, 994jawUDFY1PvHPzp+l9xg=="})
    public void encryptTest_expectedEncryptedString_whenKeyAndValueProvided(String passwd, String expResult) throws Exception {
        String result = aeSenc.encrypt(passwd, key);
        assertEquals(expResult, result);
    }

    @Test
    @Parameters({"WoivmgKUBEI7ijm8e/jcvw==, przykladowe11","vGMT+GbskFgGAzOnJF7jdA==, haslohaslo123"})
    public void decryptTest_expectedDecryptedString_whenKeyAndValueProvided(String passwd, String expResult) throws Exception{
        String result = aeSenc.decrypt(passwd,key);
        assertEquals(expResult,result);
    }

}
