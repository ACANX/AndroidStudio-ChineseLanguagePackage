package com.acanx.aslpt.util;

public class CharConvert {


    public static String chineseToUnicode(String s) {

        StringBuilder sb = new StringBuilder();
        if (null !=s){


            if (s.contains("\\\n")){
                String[] str = s.split("\\\n");
                for (int i = 0; i < str.length; i++) {
                    sb.append(chineseToUnicode(str[i]));
                    if (i < str.length - 1) {
                        sb.append("\\\n");  //
                    }
                }
                return sb.toString();
            }
            if (s.contains("\n")){
                String[] str = s.split("\n");
                for (int i = 0; i < str.length; i++) {
                    sb.append(chineseToUnicode(str[i]));
                    if (i < str.length - 1) {
                        sb.append("\\u005c\\u006e");  //
                    }
                }
                return sb.toString();
            }

            if (s.contains("\\\r")){
                String[] str = s.split("\\\r");
                for (int i = 0; i < str.length; i++) {
                    sb.append(chineseToUnicode(str[i]));
                    if (i < str.length - 1) {
                        sb.append("\\\r");
                    }
                }
                return sb.toString();
            }

            if (s.contains("\\\"")){
                String[] str = s.split("\\\"");
                for (int i = 0; i < str.length; i++) {
                    sb.append(chineseToUnicode(str[i]));
                    if (i < str.length - 1) {
                        sb.append("\\\"");
                    }
                }
                return sb.toString();
            }


            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (String.valueOf(c).matches("[\\u4e00-\\u9fff]")){
                    sb.append(String.format("\\u%04x", (int) c));
                } else {
                    sb.append(c);
                }
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
