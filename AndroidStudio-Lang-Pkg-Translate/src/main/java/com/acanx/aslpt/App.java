package com.acanx.aslpt;

import com.acanx.aslpt.constant.Constant;
import com.acanx.aslpt.domain.TranslateItem;
import com.acanx.aslpt.excel.listener.TranslateDataListener;
import com.acanx.aslpt.excel.model.BasicCellData;
import com.acanx.aslpt.util.CharConvert;
import com.acanx.aslpt.util.FileUtil;
import com.acanx.aslpt.util.PropertiesFileFilter;
import com.acanx.aslpt.util.PropertiesUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Hello World!");
        logger.info(new File(Constant.AS_CN_PACKAGE_DIR).getAbsolutePath());


        // // 配置信息读取并导出到Excel
        // // 配置信息读取
        // List<TranslateItem> items = getAllTranslateItemList(getAllPropertiesFile());
        // File file = new File(Constant.AS_CN_PACKAGE_MID_EXCEL_FILE_PATH);
        // // 导出到Excel
        // exportDataToExcelFile(items, file);


//        // 从Excel文件中读取配置，并在修改后保存到文件中
//        // 读取Excel文件中配置，加载到内存
//        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
//        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
//        // 循环请求GoogleTranslate将其翻译成对应的文本
//        // 保存翻译后的结果（分批保存）  此处批量保存存在bug，读取时会遇到标签解析异常的错误，需要重启再处理
//        EasyExcel.read(Constant.AS_CN_PACKAGE_MID_EXCEL_FILE_PATH, TranslateItem.class,
//                new TranslateDataListener()).sheet().doRead();
//        logger.info("---------------------------------------------------------------------------------------------------");


        Map<String, Map<String,String>> needChangePropMap = new HashMap<>();
        try {
            int batch = 500;
            AtomicInteger count = new AtomicInteger(0);
            EasyExcel.read(Constant.AS_CN_PACKAGE_MID_EXCEL_FILE_PATH, TranslateItem.class,
                    new PageReadListener<TranslateItem>(list -> {
                        logger.info("已完成" + list.size() + "条数据读取...");
                        for (TranslateItem item : list) {
                            logger.info("【数据】=>" + JSONObject.toJSONString(item));
                            count.incrementAndGet();
                            Map<String,String> subMap = needChangePropMap.get(item.getFile());
                            String unicodeText = CharConvert.chineseToUnicode(item.getTranslatedValue());
                            if (null == subMap) {
                                subMap = new HashMap<>();
                                subMap.put(item.getKey(), unicodeText);
                                needChangePropMap.put(item.getFile(), subMap);
                            } else {
                                subMap.put(item.getKey(), unicodeText);
                                needChangePropMap.put(item.getFile(), subMap);
                            }
                        }
                        logger.info("本批次读取到" + count.get() + "条数据");
                    }, batch)).sheet().doRead();
        } catch (Exception e) {
            logger.error("读取Excel数据异常，", e);
        }
        for (String fileKey : needChangePropMap.keySet()) {
            PropertiesUtil.modifyProperties(new File(fileKey), needChangePropMap.get(fileKey),false);
            for (String key : needChangePropMap.get(fileKey).keySet()) {
                logger.warn(String.format("[%-60s][%-100s][%s]", fileKey, key, needChangePropMap.get(fileKey).get(key)));
            }
        }



    }


    /**
     *
     * @return
     */
    public static List<File> getAllPropertiesFile(){
        List<File> files = new ArrayList<File>();
        File dir = new File(Constant.AS_CN_PACKAGE_DIR);
        PropertiesFileFilter fileFilter = new PropertiesFileFilter();
        List<File> pFileList = FileUtil.getSingleFileList(dir, fileFilter);
        for (File file : pFileList) {
            files.add(file);
        }
        return files;
    }


    /**
     *
     * @return
     */
    public static List<TranslateItem> getAllTranslateItemList(List<File> fileList){
        // 导出数据到文件
        Properties prop = new Properties();

        List<TranslateItem> translateItemList = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            try (InputStream input = new FileInputStream(fileList.get(i))) {
                // 加载properties文件
                prop.load(input);
                // 遍历properties
                File file = fileList.get(i);
                String fileName = file.getName();
                String fi = String.valueOf(i);
                prop.forEach((key, value) -> {
                    // System.out.println(String.format("[%s] [Key]:[%-60s][Value]:[%-80s] ", fileName, key, value));
                    TranslateItem item = new TranslateItem();
                    item.setFile(file.getAbsolutePath());
                    item.setFileIndex(Integer.valueOf(fi));
                    item.setGlobalIndex(0);
                    item.setKey(key.toString());
                    item.setOriginValue(value.toString());
                    item.setTranslatedValue(" ");
                    item.setTransFlag('0');
                    translateItemList.add(item);
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // 输出到文件
        int fi = 1;
        Map<String, String> keyMap = new HashMap<>();
        for (int i = 0; i < translateItemList.size(); i++) {
            TranslateItem item = translateItemList.get(i);
            item.setGlobalIndex(i + 1);
            if (!keyMap.containsKey(item.getFile())) {
                fi = 1;
                keyMap.put(item.getFile(), "1");
            } else {
                fi++;
            }
            if (CharConvert.isChinese(item.getOriginValue())){
                item.setTranslatedValue(item.getOriginValue());
                item.setTransFlag('1');
            }
            translateItemList.get(i).setFileIndex(fi);
            logger.info(JSONObject.toJSONString(translateItemList.get(i), JSONWriter.Feature.PrettyFormat));
        }

        logger.info("总行数： " + translateItemList.size());
        return translateItemList;
    }

    /**
     *
     * @param data
     * @param file
     */
    private static void exportDataToExcelFile(List<TranslateItem> data, File file){
        EasyExcel.write(file.getAbsolutePath(), TranslateItem.class).sheet("AS翻译项目映射")
                .doWrite(() -> {
                    return data;
                });
    }



}
