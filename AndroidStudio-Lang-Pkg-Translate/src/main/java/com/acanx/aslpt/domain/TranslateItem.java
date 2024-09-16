package com.acanx.aslpt.domain;

import com.acanx.aslpt.excel.convert.CharacterConverter;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 *
 */
@Data
@ExcelIgnoreUnannotated // 或略未加注解的字段
public class TranslateItem {

    @ExcelProperty("全局序号")
    private Integer globalIndex;

    @ExcelProperty("文件")
    private String file;

    @ExcelProperty("文件中的序号")
    private Integer fileIndex;

    @ExcelProperty("翻译项")
    private String key;

    @ExcelProperty("原始文本")
    private String originValue;

    /**
     *  0 未翻译
     *  1 已翻译
     *  2 已手工翻译
     *  3 特殊字符需要手工翻译
     *  4 未定义的处理标志
     */
    @ExcelProperty(value = "翻译标志", converter = CharacterConverter.class)
    private Character transFlag;

    @ExcelProperty("翻译后文本")
    private String translatedValue;


    public TranslateItem(Integer globalIndex, String file, Integer fileIndex, String key, String originValue, Character transFlag, String translatedValue) {
        this.globalIndex = globalIndex;
        this.file = file;
        this.fileIndex = fileIndex;
        this.key = key;
        this.originValue = originValue;
        this.transFlag = transFlag;
        this.translatedValue = translatedValue;
    }

    public TranslateItem() {
    }

    public Integer getGlobalIndex() {
        return globalIndex;
    }

    public void setGlobalIndex(Integer globalIndex) {
        this.globalIndex = globalIndex;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(Integer fileIndex) {
        this.fileIndex = fileIndex;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOriginValue() {
        return originValue;
    }

    public void setOriginValue(String originValue) {
        this.originValue = originValue;
    }

    public Character getTransFlag() {
        return transFlag;
    }

    public void setTransFlag(Character transFlag) {
        this.transFlag = transFlag;
    }

    public String getTranslatedValue() {
        return translatedValue;
    }

    public void setTranslatedValue(String translatedValue) {
        this.translatedValue = translatedValue;
    }

}
