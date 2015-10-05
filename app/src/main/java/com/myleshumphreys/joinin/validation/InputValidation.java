package com.myleshumphreys.joinin.validation;

public class InputValidation {
    public static boolean IsNotNullOrEmpty(String text) {
        return text != null && !text.isEmpty();
    }
}