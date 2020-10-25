package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateCssPropertyInSelector;

import java.util.HashMap;
import java.util.Map;

public class CssSelectorCodeItem implements CodeGeneratedItem {
    static String SELECTOR_PREFIX = "_";

    protected Map<String, CssPropertyCodeItem> cssProperties;
    protected String selector;
    protected String pseudoClass;
    protected boolean ownerTag = false;

    public CssSelectorCodeItem() {
//        this.selector = selector;
        cssProperties = new HashMap<>();
//        cssProperties.put(css.getKey(), css);
//        this.reChangeSelector();
    }

    public CssSelectorCodeItem(boolean ownerTag) {
        this();
        this.ownerTag = ownerTag;
    }

    private void reChangeSelector() {
        selector = Integer.toHexString(hashCode());
    }

    public void addProperty(CssPropertyCodeItem propertyCodeItem) throws DuplicateCssPropertyInSelector {
        if (cssProperties.containsKey(propertyCodeItem.getKey())) {
            throw new DuplicateCssPropertyInSelector("Css property exist with key " + propertyCodeItem.getKey());
        }

        cssProperties.put(propertyCodeItem.getKey(),  propertyCodeItem);
        reChangeSelector();

    }

    public Map<String, CssPropertyCodeItem> getCssProperties() {
        return cssProperties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CssSelectorCodeItem)) return false;

        CssSelectorCodeItem that = (CssSelectorCodeItem) o;

        return selector.hashCode() == that.hashCode();
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
    public String toString() {
        return getClass().getName() + "@" + hashCode();
    }

    @Override
    public String getContent() {
        
        String result = "." + getSelectorClass();

        if (pseudoClass != null) {
//            result += ':' + pseudoClass;
        }
        result += "{ \n";;
//        System.out.println("build ----------");
        for (Map.Entry<String, CssPropertyCodeItem> item :
                cssProperties.entrySet()) {
            CssPropertyCodeItem css = item.getValue();
//        System.out.println(item.getKey());
            result += css.getContent() + "\n";
        }

        result += "}\n";
        
        return result;
    }

    public String getSelectorClass() {
        if (pseudoClass != null) {
            return CssSelectorCodeItem.SELECTOR_PREFIX + selector + ":" + pseudoClass;

        }
        return CssSelectorCodeItem.SELECTOR_PREFIX + selector;
//        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
//        return CssSelectorCodeItem.SELECTOR_PREFIX + Integer.toHexString(hashCode());
        return selector;
    }

    public String getPseudoClass() {
        return pseudoClass;
    }

    public void setPseudoClass(String pseudoClass) {
        this.pseudoClass = pseudoClass;
    }

    public boolean isOwnerTag() {
        return ownerTag;
    }
}
