package com.acanx.aslpt.util;

public class CharConvert {


    public static String chineseToUnicode(String s) {
        StringBuilder sb = new StringBuilder();
        if (null !=s){
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                sb.append(String.format("\\u%04x", (int) c));
            }
        }
        return sb.toString();
    }


    public static String unicodeToString(){
        // TODO
        return "";
    }


    public static boolean isChinese(String str) {
        // 使用正则表达式去除特殊字符
        String symbols = "，。、（）：\"“”'()-—_";
        String pattern = "[" + symbols + "]";
        String noSymbols = str.replaceAll(pattern, "");
        return noSymbols.matches("^[\u4e00-\u9fff]+$");
    }
}
