package com.myleshumphreys.joinin.validation;

public class InputValidation {
    public static boolean IsNullOrEmpty(String text) {
        return text != null && !text.isEmpty();
    }
}