package com.revauc.revolutionbuy.util;

/**
 *  06/07/17.
 */

public class UsernameInputFilter extends RegexInputFilter {

    private static final String SPECIAL_CHARACTER_REGEX = "[ a-zA-Z0-9]+";

    public UsernameInputFilter() {
        super(SPECIAL_CHARACTER_REGEX);
    }
}