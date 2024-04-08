package dev.r4g309.utils;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {
    private final Scanner userScanner = new Scanner(System.in);

    public int getInt() {
        try {
            return userScanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Error: unable to parse input as integer");
            return 0;
        }
    }

    public BigDecimal getBigDecimal() {
        try {
            return userScanner.nextBigDecimal();
        } catch (InputMismatchException e) {
            System.err.println("Error: invalid amount");
            return BigDecimal.ZERO;
        }
    }

    public void flush() {
        userScanner.nextLine();
    }

    public String getString() {
        try {
            return userScanner.nextLine();
        } catch (InputMismatchException e) {
            System.err.println("Error: " + e.getMessage());
            return "";
        }
    }
}
