package com.groupfour.MedicalCare.Utill;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordCheck {

    private static final int LOG_ROUNDS = 10;

    public  PasswordCheck(){

    }

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS));
    }

    public static boolean  verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

}

