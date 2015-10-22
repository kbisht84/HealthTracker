package com.kanakb.healthtracker.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kanak#b on 10/15/2015.
 */
public class Validation {

    public static boolean isValidPassword(String pass) {
        String PASSWORD_PATTERN = "((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }
}
