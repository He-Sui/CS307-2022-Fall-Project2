package com.proj.sustc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mobile {
    public static boolean isMobile(String input) {

        Pattern pattern = Pattern.compile("^1[34578][0-9]{9}$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();

    };

}

