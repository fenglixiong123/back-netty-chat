package com.flx.netty.chat.common.utils.code;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by digi on 2015/12/24.
 */
public class SecurityTools {

    public static SecretKeySpec generateKey2() {
        return new SecretKeySpec("12345678123456781234567812345678".getBytes(), "AES");
    }

    public static String decrypt2(String input){
        SecretKeySpec sks = generateKey2();
        // Decode the encoded data with AES
        byte[] decodedBytes;
        try {
            byte[] encodedBytes = new BASE64Decoder().decodeBuffer(input);
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, sks);
            decodedBytes = c.doFinal(encodedBytes);
            return new String(decodedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("AES decryption error");
        }
        return null;
    }

    public static void main(String[] args) {
        String text = decrypt2("hellosdfsdfsdfsdfsdfsdfsfsdfsdfsdfsfsdfsdfsf");
        System.out.println(text);
    }

}
