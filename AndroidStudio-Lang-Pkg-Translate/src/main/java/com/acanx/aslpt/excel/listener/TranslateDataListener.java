package com.acanx.aslpt.excel.listener;

import com.acanx.aslpt.constant.Constant;
import com.acanx.aslpt.domain.TranslateItem;
import com.acanx.aslpt.excel.model.BasicCellData;
import com.acanx.aslpt.util.CharConvert;
import com.acanx.aslpt.util.ExcelUtil;
import com.acanx.aslpt.util.GoogleTranslateUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class TranslateDataListener implements ReadListener<TranslateItem> {

    private static final Logger logger = LoggerFactory.getLogger(TranslateDataListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 230000;

    private static final int START_INDEX = 0;  // 默认起始处理的记录，可以根据上次处理的情况动态调整已处理过的记录，避免重复处理等待时间过长
    /**
     * 缓存的数据
     */
    private List<TranslateItem> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private File file = new File(Constant.AS_CN_PACKAGE_MID_EXCEL_FILE_PATH);


    public TranslateDataListener() {
    }


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(TranslateItem data, AnalysisContext context) {
        logger.info(String.format("加载了一条数据:【%s】", JSONObject.toJSONString(data)));
        if (data.getTransFlag().equals('0') || data.getGlobalIndex() >= START_INDEX){
            cachedDataList.add(data);
            // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
            if (cachedDataList.size() >= BATCH_COUNT) {
                 saveData();
                // 存储完成清理 list
                cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            }
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        logger.info("所有数据解析完成！");
        logger.info("Total:", context.getTotalCount());
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        logger.info("-------------------------------------------------------------------");
        logger.info(String.format("%d条数据，开始存储数据库！", cachedDataList.size()));
        List<BasicCellData> changeCellDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        for (TranslateItem item : cachedDataList) {
            Long s = System.currentTimeMillis();
            try {
                if (item.getTransFlag().equals('0') && (!StringUtils.isBlank(item.getOriginValue())) && !CharConvert.isChinese(item.getOriginValue())) {
                    logger.info("开始处理以下纪录：\n" + JSONObject.toJSONString(item, JSONWriter.Feature.PrettyFormat));
                    String translatedValue = GoogleTranslateUtil.googleTranslate(item.getOriginValue());
                    logger.info(String.format("                  [%-6d][%-40s]===>[%-40s]", item.getGlobalIndex(), item.getOriginValue(), translatedValue));
                    BasicCellData transText = new BasicCellData(1, item.getGlobalIndex() + 1, 8, translatedValue);
                    changeCellDataList.add(transText);
                    BasicCellData transFlag = new BasicCellData(1, item.getGlobalIndex() + 1, 6, "9");
                    changeCellDataList.add(transFlag);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else if (item.getTransFlag().equals('0')){
                    BasicCellData transFlag = new BasicCellData(1, item.getGlobalIndex() + 1, 6, "8");
                    changeCellDataList.add(transFlag);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Long e = System.currentTimeMillis();
            logger.info(String.format("【%d】翻译总耗时：%d ms;",item.getGlobalIndex(), (e - s)));
        }
        try {
            ExcelUtil.batchWriteToExcelCells(file, changeCellDataList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        backupExcel();
        logger.info("存储数据库成功！");
        logger.info("-------------------------------------------------------------------");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void backupExcel(){
        Path sourcePath = Paths.get(file.getAbsolutePath());
        Path destinationPath = Paths.get(file.getAbsolutePath()+ ".bak");
        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            logger.info(String.format("文件[%s]复制成功！", file.getAbsolutePath()+ ".bak"));
        } catch (IOException e) {
            logger.warn(String.format("文件[%s]复制失败: %s", file.getAbsolutePath()+ ".bak",e.getMessage()) );
        }
    }
}
