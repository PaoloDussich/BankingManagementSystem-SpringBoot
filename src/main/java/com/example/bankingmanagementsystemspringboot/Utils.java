package com.example.bankingmanagementsystemspringboot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Utils {
    private static Scanner scanner = new Scanner(System.in);

    public static int readInt() {

        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public static String readString() {
        return scanner.nextLine().trim();
    }

    public static double readDouble() {
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;

    }

    public static  void logMenu() {
        System.out.println("\n");
        String timeStamp = java.time.LocalDateTime.now().toString();

        System.out.print(Utils.center("============================================================", 400) + "\n");
        System.out.println(Utils.center("Date: " + timeStamp + " UTC | Logged in: " + SessionContext.getCurrentUser().getUserName(), 430));
        System.out.print(Utils.center("============================================================", 400) + "\n");

    }


    public static String center(String text, int width) {
        int spaces = (width - text.length()) / 2;
        return " ".repeat(spaces) + text;
    }


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
