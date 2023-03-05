package com.demo.leetcode;

import org.apache.commons.lang3.StringUtils;

/**
 * @author chris
 * @create 2022/7/24
 */
public class Convert {
    public static String zConvert(String s, int rows) {
        if (StringUtils.isEmpty(s) || rows < 1) {
            return null;
        }
        if (rows == 1 || rows > s.length()) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        int n = rows * 2 - 2;
        int length = s.length();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < length; j++) {
                if (j % n == i || j % n == (n - i)) {
                    sb.append(s.charAt(j));
                }
            }
        }
        return sb.toString();
    }
}
