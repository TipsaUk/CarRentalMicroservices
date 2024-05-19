package ru.tipsauk.payment.util;

import java.util.UUID;

public class PaymentGenerator {

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

}
