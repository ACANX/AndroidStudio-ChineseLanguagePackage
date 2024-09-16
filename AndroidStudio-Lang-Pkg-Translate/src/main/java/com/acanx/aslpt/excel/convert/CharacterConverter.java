package com.acanx.aslpt.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class CharacterConverter implements Converter<Character> {

    @Override
    public Class<Character> supportJavaTypeKey() {
        return Character.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Character convertToJavaData(ReadCellData cellData, ExcelContentProperty excelContentProperty,
                                       GlobalConfiguration globalConfiguration) throws Exception {
        // 从excel中读数据时被EasyExcel调用
        String stringValue = cellData.getStringValue();
        // 用json转换工具将excel单元格中数据转换为java List<String>对象
        Character ch = '0';
        if (null != stringValue) {
            ch = stringValue.charAt(0);
        }
        return ch;
    }

    @Override
    public WriteCellData convertToExcelData(Character ch, ExcelContentProperty excelContentProperty
            , GlobalConfiguration globalConfiguration) throws Exception {
        // 写excel文件时被EasyExcel调用
        // 用json转换工具将List对象转换为json字符串
        String json = String.valueOf(ch);
        return new WriteCellData(json);
    }
}
