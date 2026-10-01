package com.example.bankingmanagementsystemspringboot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Utils {

    //hash de la password
    public static String hashSha(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(password.getBytes());
            StringBuilder hexaDecimalString = new StringBuilder();

            for (byte b : hash) {
                String hexa = Integer.toHexString(0xff & b);

                if (hexa.length() == 1) {
                    hexaDecimalString.append('0');
                }

                hexaDecimalString.append(hexa);
            }

            return hexaDecimalString.toString();

        } catch (NoSuchAlgorithmException error) {
            throw new RuntimeException(error);
        }

    }

}
