package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssSelectorCodeItem;

import java.util.Map;

public abstract class SelectorBuilder {

    private Map<String, CssSelectorCodeItem> selectors;

    public SelectorBuilder(Map<String, CssSelectorCodeItem> selectors) {
        this.selectors = selectors;
    }

    public abstract CssSelectorCodeItem build();
}
