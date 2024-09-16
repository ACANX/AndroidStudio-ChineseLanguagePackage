package com.acanx.aslpt;

import com.acanx.aslpt.util.CharConvert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChineseToUnicodeTest {

    @Test
    public void toChineseTest() {
        String chinese = "汉字中华人民共和国";
        String unicode = CharConvert.chineseToUnicode(chinese);
        System.out.println(unicode);
    }



    @Test
    public void checkChineseTest() {
        String chinese = "在不同的驼峰模式中移动插入符到上一个词";
        String chinese2 = "类型迁移...";
        String chinese3 = "反转_布尔 (Boolean)...";
        String chinese4 = "Navigate to the implementation(s) of the selected class or method";
        System.out.println(CharConvert.isChinese(chinese));
        System.out.println(CharConvert.isChinese(chinese2));
        System.out.println(CharConvert.isChinese(chinese3));
        System.out.println(CharConvert.isChinese(chinese4));
    }







}