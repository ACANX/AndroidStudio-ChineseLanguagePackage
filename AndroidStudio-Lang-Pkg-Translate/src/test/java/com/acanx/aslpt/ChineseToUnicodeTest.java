package com.acanx.aslpt;

import com.acanx.aslpt.util.CharConvert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChineseToUnicodeTest {

    @Test
    public void toChineseTest() {
        String chinese = "输出目录“{0}”不存在\\n是否要创建它？";
        String unicode = CharConvert.chineseToUnicode(chinese);
        System.out.println(unicode);
        System.out.println("------------------------------------");

        String string2 = "项目类文件已过期。\n您是否要在继续 DSM 分析之前编译项目？\n拒绝执行此操作可能会导致数据不完整或不正确。";
        String unicode2 = CharConvert.chineseToUnicode(string2);
        System.out.println(unicode2);
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