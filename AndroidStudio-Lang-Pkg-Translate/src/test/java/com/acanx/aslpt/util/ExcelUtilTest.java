package com.acanx.aslpt.util;

import com.acanx.aslpt.excel.model.BasicCellData;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelUtilTest {

    @Test
    void batchWriteToExcelCellsByEasyExcel() {


        BasicCellData basicCellData = new BasicCellData(1,1,1,"Test1");
        BasicCellData basicCellData2 = new BasicCellData(1,3,2,"Test2");

        List<BasicCellData> list = new ArrayList<BasicCellData>();
        list.add(basicCellData);
        list.add(basicCellData2);

        File file = new File("D:\\Code\\JavaCode\\AndroidStudio-ChineseLanguagePackage\\AndroidStudio_v2.0.0.20_zh-cn\\resources_cn\\Test.xlsx");

        try {
            ExcelUtil.batchWriteToExcelCellsByEasyExcel(file, list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}