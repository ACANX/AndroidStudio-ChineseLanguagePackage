package com.acanx.aslpt;

import com.acanx.aslpt.constant.Constant;
import com.acanx.aslpt.domain.TranslateItem;
import com.acanx.aslpt.util.FileUtil;
import com.acanx.aslpt.util.GoogleTranslateUtil;
import com.acanx.aslpt.util.PropertiesFileFilter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


class TranslateTest {



    @Test
    public void googleTranslateTest() throws MalformedURLException, UnsupportedEncodingException {
        String text = GoogleTranslateUtil.googleTranslate("''{0}'' is bad configured");
        System.out.println(text);
    }


    @Test
    @Deprecated
    public void gTest(){
        Properties properties;

        File dir = new File(Constant.AS_CN_PACKAGE_DIR);
        PropertiesFileFilter fileFilter = new PropertiesFileFilter();
        List<File> pFileList = FileUtil.getSingleFileList(dir, fileFilter);
        for (File file : pFileList) {
            System.out.println(file.getAbsolutePath());
        }

        // 读取每个配置文件的每一项配置

    }




    @Test
    public void readConfigKeyAndValue() {



    }


}