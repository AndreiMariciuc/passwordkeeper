package com.familypassword.utils;

import java.util.Random;
import java.util.UUID;

public class TokenGenerator {

    public static String generateToken() {
        return new UUID(new Random().nextLong(), new Random().nextLong()).toString();
    }
}
