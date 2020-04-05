package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;

import java.util.Map;

public class CssPropertyCodeItem implements CodeGeneratedItem {

    private String key;
    private String value;
    private CssStyle css;

    public CssPropertyCodeItem(CssStyle css) throws Exception {
        this.css = css;
        this.key = css.getName();
        this.value = css.getFullValue();
    }

    @Override
    public int hashCode() {

        return css.hashCode();
    }

    @Override
    public String getContent() {
        return key + ": " + value + ";";
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }



}
