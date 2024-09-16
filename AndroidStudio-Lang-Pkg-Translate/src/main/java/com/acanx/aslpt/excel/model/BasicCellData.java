package com.acanx.aslpt.excel.model;

public class BasicCellData<T> {

    private int sheetNo;

    private String sheetName;

    private int row;

    private int col;

    private T value;

    // 泛型构造函数
    public BasicCellData(T t) {
        this.value = t;
    }

    public BasicCellData(int sheetNo, int row, int col, T value) {
        this.sheetNo = sheetNo;
        this.row = row;
        this.col = col;
        this.value = value;
    }

    // 泛型方法
    public T get() {
        return value;
    }

    public void set(T t) {
        this.value = t;
    }


    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(int sheetNo) {
        this.sheetNo = sheetNo;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
