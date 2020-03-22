package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;

import java.util.ArrayList;
import java.util.List;

public abstract class HtmlNodeCodeItem implements CodeGeneratedItem {

    protected StringBuilder stringBuilder;
    public void setStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }


}
