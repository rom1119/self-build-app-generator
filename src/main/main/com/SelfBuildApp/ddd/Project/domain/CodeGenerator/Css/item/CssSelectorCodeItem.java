package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateCssPropertyInSelector;

import java.util.HashMap;
import java.util.Map;

public class CssSelectorCodeItem implements CodeGeneratedItem {
    static String SELECTOR_PREFIX = "_";

    protected Map<String, CssPropertyCodeItem> cssProperties;
    public String selector;

    public CssSelectorCodeItem(String selector) {
        this.selector = selector;
        cssProperties = new HashMap<>();

    }

    public void addProperty(CssPropertyCodeItem propertyCodeItem) throws DuplicateCssPropertyInSelector {
        if (cssProperties.containsKey(propertyCodeItem.getKey())) {
            throw new DuplicateCssPropertyInSelector("Css property exist with key " + propertyCodeItem.getKey());
        }

        cssProperties.put(propertyCodeItem.getKey(),  propertyCodeItem);

    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (Map.Entry<String, CssPropertyCodeItem> el: cssProperties.entrySet()) {
            hash += el.getValue().hashCode();
        }
        return hash;
    }

    @Override
    public String getContent() {
        
        String result = "." + CssSelectorCodeItem.SELECTOR_PREFIX + selector + "{ \n";

        for (Map.Entry<String, CssPropertyCodeItem> item :
                cssProperties.entrySet()) {
            CssPropertyCodeItem css = item.getValue();
            result += css.getContent() + "\n";
        }

        result += "}\n";
        
        return result;
    }

    public String getSelector() {
        return Integer.toHexString(hashCode());
    }
}
