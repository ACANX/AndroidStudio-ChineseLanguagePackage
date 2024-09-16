package com.acanx.aslpt.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesUtilTest {

    @Test
    void modifyProperties() {
        File test = new File("D:\\Code\\JavaCode\\AndroidStudio-ChineseLanguagePackage\\AndroidStudio_v2.0.0.20_zh-cn\\resources_cn\\messages\\ActionsBundle.properties");

        String key1 = "action.ContextHelp.description";
        String value1 = "测试配置值AAAAAAAAAAAAAA";
        String key2 = "action.$Paste.description";
        String value2 = "测试配置值BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

        PropertiesUtil.modifyProperties(test, key1, value1);
        PropertiesUtil.modifyProperties(test, key2, value2);

    }
}