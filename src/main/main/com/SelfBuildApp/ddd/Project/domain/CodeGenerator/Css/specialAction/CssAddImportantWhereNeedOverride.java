package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.specialAction;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.CssAction;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssPropertyCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssSelectorCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlTagCodeItem;

import java.util.Map;

public class CssAddImportantWhereNeedOverride implements CssAction {

    protected String cssName;

    public CssAddImportantWhereNeedOverride(String cssName) {
        this.cssName = cssName;
    }

    @Override
    public boolean canRun() {
        return cssName.contains("left") ||
                cssName.contains("right") ||
                cssName.contains("top") ||
                cssName.contains("bottom")
                ;
    }

    @Override
    public void run() {



    }
}
