package com.syp.board_back.common.util;

public class StringToNumber {

    public static boolean strToNum(String strParam) {
        if (strParam.isEmpty()) {
            return false;
        }

        for (int i = 0; i < strParam.length(); i++) {
            int tmp = strParam.charAt(i);
            if (!Character.isDigit(tmp)) {
                return false;
            }
        }
        return true;
    }
}
