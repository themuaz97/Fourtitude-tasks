package com.fourtitude;

import java.util.Base64;
import java.util.Scanner;

public class Tasks {
    public static void main(String[] args) {
        /* Select & Uncomment ONE method to run.. */

        // task1Basic();
        // task1Advance();
        // task2Basic();
        // task2Advance();
        // task3Basic();
        task3Advance();
    }

    /* -------------------------task 1(Basic)----------------------------- */

    public static void task1Basic() {
        // Loan details
        double loanAmount = 400000; // RM
        double annualInterestRate = 0.04; // 4.00%
        int loanTermInYears = 30;

        // Monthly interest rate
        double monthlyInterestRate = annualInterestRate / 12;

        // Total number of payments
        int numberOfPayments = loanTermInYears * 12;

        // Calculate monthly repayment using the loan payment formula
        double monthlyRepayment = (loanAmount * monthlyInterestRate)
                / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));

        // Display the result
        System.out.printf("Loan Amount: RM %.2f\n", loanAmount);
        System.out.printf("Annual Interest Rate: %.2f%%\n", annualInterestRate * 100);
        System.out.printf("Loan Term: %d years\n", loanTermInYears);
        System.out.printf("Monthly Repayment: RM %.2f\n", monthlyRepayment);
    }

    /* -------------------------task 1(Advance)-------------------------- */

    public static void task1Advance() {
        // Loan details
        double loanAmount = 400000; // RM
        int desiredMonthlyPayment = 1500; // RM
        int loanTermYears = 30;

        // Binary search for the acceptable interest rate
        double lowerRate = 0.001; // Starting from a very low rate
        double upperRate = 1.0; // Assume 100% interest (unrealistic upper bound)

        double acceptableRate = findAcceptableRate(loanAmount, desiredMonthlyPayment, loanTermYears, lowerRate,
                upperRate);

        // Display the result
        System.out.printf("Acceptable Interest Rate: %.2f%%%n", acceptableRate * 100);
    }

    private static double findAcceptableRate(double loanAmount, int desiredMonthlyPayment, int loanTermYears,
            double lowerRate, double upperRate) {
        double tolerance = 0.01; // Tolerance for acceptable difference
        double midRate;

        while ((upperRate - lowerRate) > tolerance) {
            midRate = (lowerRate + upperRate) / 2;

            // Calculate monthly repayment using the loan payment formula
            double monthlyInterestRate = midRate / 12;
            int numberOfPayments = loanTermYears * 12;
            double monthlyRepayment = loanAmount
                    * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments))
                    / (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);

            // Adjust the search boundaries based on the calculated monthly repayment
            if (monthlyRepayment > desiredMonthlyPayment) {
                upperRate = midRate;
            } else {
                lowerRate = midRate;
            }
        }

        return lowerRate;
    }

    /* -------------------------task 2(Basic)------------------------- */

    public static void task2Basic() {
        String sequence = "98062477123";

        // Calculate check digit
        int sum = 0;

        for (char digitChar : sequence.toCharArray()) {
            int digit = Character.getNumericValue(digitChar);
            sum += digit;

            // Check if sum is odd or even and update accordingly
            if (sum % 2 == 1) {
                sum = (sum - 1) / 2;
            } else {
                sum = sum / 2;
            }
        }

        // Display the check digit
        System.out.println("Check Digit: " + sum);
        System.out.println("The generated value: " + sequence + sum);
    }

    /* -------------------------task 2(Advance)------------------------- */

    public static void task2Advance() {
        int rangeStart = 1;
        int rangeEnd = 1000000;

        // Array to store counts for each check digit
        int[] digitCount = new int[10];

        for (int i = rangeStart; i <= rangeEnd; i++) {
            int sum = calculateCheckDigit(i);
            digitCount[sum]++;
        }

        // Display the distribution
        for (int i = 0; i < 10; i++) {
            System.out.println("Check Digit " + i + ": " + digitCount[i] + " occurrences");
        }
    }

    private static int calculateCheckDigit(int number) {
        int sum = 0;
        String digits = Integer.toString(number);

        for (char digitChar : digits.toCharArray()) {
            int digit = Character.getNumericValue(digitChar);
            sum += digit;

            if (sum % 2 == 1) {
                sum = (sum - 1) / 2;
            } else {
                sum = sum / 2;
            }
        }

        return sum;
    }

    /* -------------------------task 3(Basic)------------------------- */

    public static void task3Basic() {
        Lcg lcg = new Lcg(65536, 137, 1, 0);
        int primeIndex = 100;
        long nthPrime = lcg.nthPrime(primeIndex);

        System.out.printf("The %dth prime number in the LCG sequence is: %d\n", primeIndex, nthPrime);
    }

    /* -------------------------task 3(Advance)------------------------- */

    public static void task3Advance() {
        try {
            System.out.println("Enter a string to be encrypted: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            System.out.println("Entered string: " + input);
            String encrypted = encrypt(input);
            System.out.println("String after encryption: " + encrypted);
            String decrypted = decrypt(encrypted);
            System.out.println("String after decryption: " + decrypted);
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error occured during encryption/decryption.");
        }
    }

    public static String encrypt(String plainText) throws Exception {
        Lcg lcg = new Lcg(256, 11, 1, 0);
        byte[] bytes = plainText.getBytes("UTF-8");
        int len = bytes.length;
        byte[] xors = new byte[len];
        for (int ix = 0; ix < len; ix += 1) {
            xors[ix] = (byte) ((int) bytes[ix] ^ ((int) lcg.next()));
        }
        return new String(java.util.Base64.getEncoder().encode(xors), "UTF-8");
    }

    public static String decrypt(String base64EncodedValue) throws Exception {
        Lcg lcg = new Lcg(256, 11, 1, 0);
        byte[] decodedByte = Base64.getDecoder().decode(base64EncodedValue.getBytes("UTF-8"));
        int len = decodedByte.length;
        byte[] xors = new byte[len];
        for (int ix = 0; ix < len; ix += 1) {
            xors[ix] = (byte) ((int) decodedByte[ix] ^ ((int) lcg.next()));
        }
        return new String(xors, "UTF-8");
    }
}
