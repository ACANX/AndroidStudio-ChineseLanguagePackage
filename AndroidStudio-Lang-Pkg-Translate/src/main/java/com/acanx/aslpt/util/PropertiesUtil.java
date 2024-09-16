package com.acanx.aslpt.util;

import com.acanx.aslpt.constant.Constant;
import com.acanx.aslpt.domain.PropertiesItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties loadConfig(File file) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading properties: " + e.getMessage());
        }
        return properties;
    }

    public String getPropertiesFileConfigValue(File file, String key) {
        Properties properties = loadConfig(file);
        return properties.getProperty(key);
    }

    /**
     *  修改配置，（注意：此操作会修改配置项的原有顺序，如需要保证顺序请选择 @See modifyProperties ）
     * @param file
     * @param key
     * @param value
     */
    public static void setPropertiesFileConfigValue(File file, String key, String value) {
        Properties properties = loadConfig(file);
        properties.setProperty(key, value);
        saveConfig(properties, file);
    }

    private static void saveConfig(Properties properties, File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            properties.store(fos, "");
        } catch (IOException e) {
            System.err.println("Error saving properties: " + e.getMessage());
        }
    }


    /**
     *  修改配置文件的配置值，不改变配置的顺序。
     * @param file
     * @param key
     * @param value
     */
    public static void modifyProperties(File file, String key, String value) {
        if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)){
            Map<String, PropertiesItem> configMap = new HashMap<String, PropertiesItem>();
            List<String> configList;
            try {
                configList = FileUtils.readLines(file, Constant.ENCODE_UTF8);
                for (int i = 0; i < configList.size(); i++) {
                    String line = configList.get(i);
                    String[] keyValue = line.split("=");
                    if (!StringUtils.isBlank(line) && !line.startsWith("#") && keyValue.length == 2) {
                        PropertiesItem item = new PropertiesItem(keyValue[0], keyValue[1], i);
                        configMap.put(keyValue[0], item);
                    }
                }
            } catch (IOException e) {
                // TODO
                throw new RuntimeException(e);
            }
            PropertiesItem qItem = configMap.get(key);
            if(null != qItem) {
                // 存在
                String newValue = qItem.getKey() + "=" + value;
                qItem.setValue(value);
                configMap.put(key, qItem);
                configList.set(qItem.getLine(), newValue);
            } else {
                // 不存在 朱家
                String newValue = key + "=" + value;
                configList.add(newValue);
            }
            try {
                FileUtils.writeLines(file, configList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
