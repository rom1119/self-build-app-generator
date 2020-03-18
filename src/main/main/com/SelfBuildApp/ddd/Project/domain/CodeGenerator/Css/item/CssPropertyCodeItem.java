package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;

class CssPropertyCodeItem implements CodeGeneratedItem {

    private String key;
    private String value;

    public CssPropertyCodeItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getContent() {
        return key + ": " + value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
