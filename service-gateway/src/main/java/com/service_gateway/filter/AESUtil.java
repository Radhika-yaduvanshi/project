package com.service_gateway.filter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

public class AESUtil {
    static {
        Security.addProvider(new BouncyCastleProvider()); // Add Bouncy Castle provider
    }

    public static String decrypt(String encryptedText, String secretKey, String iv) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        System.out.println("Key length: " + secretKey.getBytes().length);
        System.out.println("IV length: " + iv.getBytes().length);



        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC"); // Specify PKCS7Padding
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}