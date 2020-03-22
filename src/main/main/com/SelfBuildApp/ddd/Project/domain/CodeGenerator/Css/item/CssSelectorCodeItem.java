package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateCssPropertyInSelector;

import java.util.HashMap;
import java.util.Map;

public class CssSelectorCodeItem implements CodeGeneratedItem {

    protected Map<String, CssPropertyCodeItem> cssProperties;
    public String selector;

    public CssSelectorCodeItem() {
        cssProperties = new HashMap<>();
    }

    public void addProperty(CssPropertyCodeItem propertyCodeItem) throws DuplicateCssPropertyInSelector {
        if (cssProperties.containsKey(propertyCodeItem.getKey())) {
            throw new DuplicateCssPropertyInSelector("Css property exist with key " + propertyCodeItem.getKey());
        }

        cssProperties.put(propertyCodeItem.getKey(),  propertyCodeItem);

    }

    @Override
    public String getContent() {
        
        String result = selector + "{ \n";

        for (Map.Entry<String, CssPropertyCodeItem> item :
                cssProperties.entrySet()) {
            CssPropertyCodeItem css = item.getValue();
            result += css.getContent() + "\n";
        }

        result += "}";
        
        return result;
    }
}
