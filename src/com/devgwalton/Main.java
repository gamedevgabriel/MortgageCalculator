package com.devgwalton;

import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    private static final byte MONTHS_IN_YEARS = 12;
    private static final byte PERCENT = 100;

    public static void main(String[] args) {
        int principle = (int) readNumber("Principle: ", 1_000, 1_000_000);
        float annualInterest = (float) readNumber("Annual Interest Rate: ", 1, 30);
        byte years = (byte) readNumber("Period (Years): ", 1, 30);

        double mortgage = calculateMortgage(principle, annualInterest, years);
        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);
        System.out.println();
        System.out.println("MORTGAGE");
        System.out.println("--------");
        System.out.println("Mortgage Paymanets: " + mortgageFormatted);

        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("----------------");
        for (short month = 1; month <= years * MONTHS_IN_YEARS; month++) {
            double balance = calculateBalance(principle, annualInterest, years, month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }
    }

    private static double readNumber(String prompt, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double output;
        while (true) {
            System.out.print(prompt);
            output = scanner.nextInt();
            if (output >= min && output <= max)
                break;
            System.out.println("Enter a value between " + min +  " and " + max);
        }
        return output;
    }

    private static double calculateBalance(int principle, float annualInterest, byte years, short paymentsMade) {
        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEARS;
        short numberOfPayments = (short)(years * MONTHS_IN_YEARS);

        double balance = principle
                * (Math.pow(1 + monthlyInterest, numberOfPayments)
                - Math.pow(1 + monthlyInterest, paymentsMade))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);

        return balance;
    }

    private static double calculateMortgage(int principle, float annualInterest, byte years) {
        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEARS;
        short numberOfPayments = (short)(years * MONTHS_IN_YEARS);

        double mortgage = principle
            * (monthlyInterest * Math.pow(1 + monthlyInterest, numberOfPayments))
            / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);

        return mortgage;
    }
}
