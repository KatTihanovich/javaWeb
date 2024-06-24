package com.esde.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationValidator {
    public static boolean validateAllFieldsNotEmpty(String username, String email, String password) {
        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    public static boolean validateAllFieldsNotEmpty(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }

    public static boolean validateEmailMatchesPattern(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}