package com.vaultapp.utilities;


import org.jasypt.util.text.StrongTextEncryptor;

/**
 * Provides methods for encrypting and decrypting strings using a symmetric key cipher.
 * This class uses the `StrongTextEncryptor` class from the Jasypt library to perform
 * the encryption and decryption operations.
 */
public class Cipher {
    /**
     * The `StrongTextEncryptor` instance used to perform the encryption and decryption operations.
     */
    private StrongTextEncryptor cipher;

    /**
     * The singleton instance of the `Cipher` class.
     */
    private static final Cipher instance;

    /**
     * Initializes the `instance` field with a new instance of the `Cipher` class.
     */
    static {
        instance = new Cipher();
    }

    /**
     * Constructs a new `Cipher` instance and initializes the `cipher` field with a new
     * `StrongTextEncryptor` instance that uses the password "seed" for the symmetric key.
     */
    public Cipher() {
        cipher = new StrongTextEncryptor();
        cipher.setPassword("seed");
    }

    /**
     * Returns the singleton instance of the `Cipher` class.
     *
     * @return the singleton instance of the `Cipher` class
     */
    public static Cipher getInstance() {
        return instance;
    }

    /**
     * Encrypts the specified string using the symmetric key cipher.
     *
     * @param str the string to encrypt
     * @return the encrypted string
     */
    public String encrypt(String str) {
        return cipher.encrypt(str);
    }

    /**
     * Decrypts the specified string using the symmetric key cipher.
     *
     * @param str the string to decrypt
     * @return the decrypted string
     */
    public String decrypt(String str) {
        return cipher.decrypt(str);
    }
}
