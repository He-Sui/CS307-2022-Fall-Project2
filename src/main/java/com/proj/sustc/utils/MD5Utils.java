package com.proj.sustc.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;


@Component
public class MD5Utils {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToForm(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String secondPass(String FirstPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + FirstPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String salt) {
        String FormPass = inputPassToForm(inputPass);
        String DbPass = secondPass(FormPass, salt);
        return DbPass;
    }


}