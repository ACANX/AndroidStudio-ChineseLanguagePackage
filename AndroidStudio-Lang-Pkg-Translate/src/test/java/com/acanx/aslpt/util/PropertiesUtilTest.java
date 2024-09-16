package com.acanx.aslpt.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    void testModifyProperties2() {


        File file = new File("D:\\Code\\JavaCode\\AndroidStudio-ChineseLanguagePackage\\AndroidStudio_v2.0.0.20_zh-cn\\OptionsBundle.properties");

        String key1 = "configurable.group.appearance.settings.description";
        String value1 = "测试配置值AAAAAAAAAAAAAA";
        String key2 = "configurable.group.editor.settings.description";
        String value2 = "测试配置值BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
        String key3 = "configurable.group.project.settings.description";
        String value3 = "测试配置值CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC";
        String key4 = "configurable.group.build.settings.description";
        String value4 = "测试配置值DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD";
        String key5 = "configurable.group.build.tools.settings.description";
        String value5 = "测试配置值EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE";

        Map<String, String> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        map.put(key4, value4);
        map.put(key5, value5);
        PropertiesUtil.modifyProperties(file, map, false);




    }
}