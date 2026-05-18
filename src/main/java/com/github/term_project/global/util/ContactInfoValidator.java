package com.github.term_project.global.util;

import java.util.regex.Pattern;

public final class ContactInfoValidator {

    private static final Pattern EMAIL = Pattern.compile("[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}");
    private static final Pattern PHONE = Pattern.compile("\\d{2,3}[-\\s.]?\\d{3,4}[-\\s.]?\\d{4}");

    private ContactInfoValidator() {}

    public static boolean containsContactInfo(String text) {
        if (text == null || text.isBlank()) {
            return false;
        }
        return EMAIL.matcher(text).find() || PHONE.matcher(text).find();
    }
}
