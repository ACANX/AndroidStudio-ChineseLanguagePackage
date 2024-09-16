package com.acanx.aslpt.domain;

import lombok.Data;

@Data
public class PropertiesItem {

    private String key;

    private String value;

    private Integer line;

    public PropertiesItem(String key, String value, Integer line) {
        this.key = key;
        this.value = value;
        this.line = line;
    }
}
