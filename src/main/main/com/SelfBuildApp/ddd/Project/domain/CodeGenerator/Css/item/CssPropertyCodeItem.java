package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.specialAction.CssAddImportantWhereNeedOverride;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;

import java.util.Map;

public class CssPropertyCodeItem implements CodeGeneratedItem {

    private String key;
    private String value;
    private CssStyle css;
    private boolean important;

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
//        System.out.println(key);
//        System.out.println(hashCode());
        if (isImportant() || hasDirectionInName()) {
            return key + ": " + value + " !important" + ";";

        } else {
            return key + ": " + value + ";";

        }
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getIdentity()
    {
        return css.getCssIdentity();
    }

    public boolean isImportant() {
        return important;
    }

    public boolean hasDirectionInName() {
        CssAddImportantWhereNeedOverride cssAddImportantWhereNeedOverride = new CssAddImportantWhereNeedOverride(this.key);
        return cssAddImportantWhereNeedOverride.canRun();
    }
}
