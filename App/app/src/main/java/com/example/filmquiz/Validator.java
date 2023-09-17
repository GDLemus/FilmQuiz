package com.example.filmquiz;

import java.util.regex.Pattern;

public class Validator {

    public static boolean validatePhone(String phoneNumber) {

        String regex = "\\d{8}";
        return Pattern.matches(regex, phoneNumber);
    }

    public static boolean validateEmail(String email) {

        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(regex, email);
    }

    public static boolean validateString(String input) {
        String regex = "^[A-Za-z0-9]+$";
        return Pattern.matches(regex, input);

    }

    public static boolean validatePassword(String password) {
        // La contraseña debe tener al menos 8 caracteres, contener al menos una letra mayúscula, una letra minúscula y un dígito.
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return Pattern.matches(regex, password);
    }

    public static boolean validateUsername(String username) {
        // El nombre de usuario no debe contener números ni símbolos.
        String regex = "^[A-Za-z]+$";
        return Pattern.matches(regex, username);
    }
}

