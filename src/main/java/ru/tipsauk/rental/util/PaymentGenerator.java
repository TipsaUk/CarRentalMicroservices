package ru.tipsauk.rental.util;

import java.util.Random;


public class PaymentGenerator {

    public static int generateSumPayment() {
        int minAmount = 1000;
        int maxAmount = 10000;
        Random random = new Random();
        int randomAmount =  random.nextInt(maxAmount - minAmount + 1) + minAmount;
        return ((randomAmount + 50) / 100) * 100;
    }

}
