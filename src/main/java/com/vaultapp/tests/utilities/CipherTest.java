package com.vaultapp.tests.utilities;

import static org.junit.jupiter.api.Assertions.*;

import com.vaultapp.utilities.Cipher;
import org.junit.jupiter.api.Test;

public class CipherTest {

    @Test
    void testEncryptDecrypt() {
        Cipher cipher = Cipher.getInstance();
        String plaintext = "Hello, world!";
        String encrypted = cipher.encrypt(plaintext);
        String decrypted = cipher.decrypt(encrypted);
        assertEquals(plaintext, decrypted);
    }
}
