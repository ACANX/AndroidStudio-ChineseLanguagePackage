package com.acanx.aslpt.util;


import com.acanx.aslpt.excel.model.BasicCellData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ehcache.core.util.CollectionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

//    public static boolean exportDataToExcel(List<?> dataList, String path){
//        boolean flag = false;
//        try {
//            ExcelExportUtil.exportToFile(dataList, path);
//            flag =  true;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return flag;
//    }


    /**
     *
     * @param file
     * @param sheetNo
     * @param rowNo
     * @param columnNo
     * @param content
     */
    public static void writeStringToCell(File file, Integer sheetNo, int rowNo, int columnNo, String content) throws IOException {
        // 使用Apache POI加载现有的Excel文件
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(sheetNo - 1); // 获取第sheetNo个sheet
        // 确保第rowNo行存在，如果不存在则创建
        if (sheet.getRow(rowNo-1) == null) {
            sheet.createRow(rowNo-1);
        }
        // 在第rowNo行，第columnNo列写入数据
        Row row = sheet.getRow(rowNo-1);
        Cell cell = row.createCell(columnNo-1 ); // 列索引从0开始，所以4是第五列
        cell.setCellValue(content);
        // 将修改后的workbook写回文件
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
        } finally {
            // 关闭workbook和inputStream
            workbook.close();
            inputStream.close();
        }
    }



    /**
     *
     * @param file
     * @param cellDataList
     */
    public static void batchWriteToExcelCells(File file, List<BasicCellData> cellDataList) throws IOException {
        // 使用Apache POI加载现有的Excel文件
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);

        for (BasicCellData ce : cellDataList) {
            Sheet sheet = workbook.getSheetAt(ce.getSheetNo() - 1); // 获取第sheetNo个sheet
            // 确保第rowNo行存在，如果不存在则创建
            if (sheet.getRow(ce.getRow() - 1) == null) {
                sheet.createRow(ce.getRow() - 1);
            }
            // 在第rowNo行，第columnNo列写入数据
            Row row = sheet.getRow(ce.getRow() - 1);
            Cell cell = row.createCell(ce.getCol() - 1); // 列索引从0开始，所以4是第五列
            if (ce.get() instanceof String) {
                cell.setCellValue((String) ce.get());
            } else if (ce.get() instanceof Character) {
                cell.setCellValue(String.valueOf(ce.get().toString().charAt(0)));
            } else if (ce.get() instanceof Float) {
                cell.setCellValue(((Float) ce.get()));
            } else if (ce.get() instanceof Double) {
                cell.setCellValue(((Double) ce.get()));
            } else if (ce.get() instanceof Integer) {
                cell.setCellValue(((Integer) ce.get()));
            }
        }

        // 将修改后的workbook写回文件
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
        } finally {
            // 关闭workbook和inputStream
            workbook.close();
            inputStream.close();
        }
    }




    public static void main11111(File file, int sheetNo, int row, int col, Object object) throws IOException {
        // 读取 Excel 文件路径
        // 读取 Excel 文件并加载数据到内存中
        List<Object> dataList = EasyExcel.read(file).sheet(sheetNo -1).doReadSync();
        if (dataList.size() >= row) {
            Object rowData = dataList.get(row-1);
            if (rowData instanceof List) {
                List<Object> rows = (List<Object>) rowData;
                if (col <= rows.size()) {
                    // 修改指定单元格的数据
                    rows.set(col-1, object);
                }
            }
        }
        EasyExcel.write(file).sheet(sheetNo-1).doWrite(dataList);
    }

    /**
     *
     * @param file
     * @param list
     * @throws IOException
     */
    public static void batchWriteToExcelCellsByEasyExcel(File file, List<BasicCellData> list) throws IOException {
        // 读取 Excel 文件路径
        List<Object> data = EasyExcel.read(new FileInputStream(file.getAbsolutePath())).doReadAllSync();

        // 使用 Map 来存储每个 Sheet 的数据列表
        Map<Integer, List<BasicCellData>> fillDataMap = new HashMap<>();
        for (int i = 0; i < 255; i++) {
            fillDataMap.put(i, new ArrayList<>());
        }
        // 随机分配数据到各个 Sheet
        for (BasicCellData cell : list) {
            int sheetIndex = cell.getSheetNo();
            fillDataMap.get(sheetIndex).add(cell);
        }

        try (ExcelWriter excelWriter = EasyExcel.write(file).build()) {
            for (int i = 0; i < 255; i++) {
                List<BasicCellData> sheetFillList = fillDataMap.get(i);
                if (CollectionUtils.isNotEmpty(sheetFillList)){
                    List<Object> sheetData;
                    WriteSheet writeSheet = EasyExcel.writerSheet(i).build();
                    if (data.size() > i) {
                        sheetData = linkedHashMapToList(data.get((i)));
                    } else {
                        sheetData = new ArrayList<>();
                        excelWriter.write(new ArrayList<>(), writeSheet);
                    }
                    for (BasicCellData cell : sheetFillList) {
                        Object rowData = sheetData.get(cell.getRow()-1);
                        List<Object> rows = linkedHashMapToList(rowData);
                        if (cell.getCol() <= rows.size()) {
                            // 修改指定单元格的数据
                            rows.set(cell.getCol()-1, cell.get());
                        }
                    }
                    excelWriter.write(sheetData, writeSheet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param obj
     * @return
     */
    private static List<Object> linkedHashMapToList(Object obj) {
        List<Object> list = new ArrayList<>();
        if (obj instanceof LinkedHashMap) {
            for (Map.Entry entry : ((LinkedHashMap<?, ?>) obj).entrySet()) {
                list.add(entry.getValue());
            }
        }
        return list;
    }

}
