package ua.glumaks.utils;


import ua.glumaks.exceptions.CryptoException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class CryptoUtil {

    public String encode(String msg) {
        return new String(Base64.getEncoder().encode(msg.getBytes(StandardCharsets.UTF_8)));
    }

    public String decode(String encoded) {
        return new String(Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8)));
    }


    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";


    public static SecretKey createAESKey(int size) throws Exception {
        KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
        keygenerator.init(size);
        return keygenerator.generateKey();
    }

    public static IvParameterSpec createInitializationVector() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String algorithm,
                                 String input,
                                 SecretKey key,
                                 IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder()
                    .encodeToString(cipherText);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidAlgorithmParameterException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptoException(e);
        }
    }

    public static String decrypt(String algorithm,
                                 String cipherText,
                                 SecretKey key,
                                 IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                    .decode(cipherText));
            return new String(plainText);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidAlgorithmParameterException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException e) {
            throw new CryptoException(e);
        }
    }

}
