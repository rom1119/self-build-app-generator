package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateCssPropertyInSelector;
import com.SelfBuildApp.ddd.Project.domain.MediaQuery;

import java.util.HashMap;
import java.util.Map;

public class MediaQueryCodeItem implements CodeGeneratedItem {

    protected Map<String, CssSelectorCodeItem> selectors;
    protected MediaQuery mediaQuery;

    public MediaQueryCodeItem(MediaQuery mediaQuery) {
        this.mediaQuery = mediaQuery;
        selectors = new HashMap<>();
//        cssProperties.put(css.getKey(), css);
//        this.reChangeSelector();
    }

    public Long getMediaQueryId()
    {
        return  mediaQuery.getId();
    }


    public void updateSelectorWithKey(String oldSelectorKey, CssSelectorCodeItem selector2) {

        selectors.remove(oldSelectorKey);
//        System.out.println(children.size());
//        addSelector(selector2);
        selectors.put(selector2.getSelectorClass(), selector2);

//        System.out.println(children.size());
//        System.out.println("end-remove");
    }

    public void addSelector(CssSelectorCodeItem propertyCodeItem) throws DuplicateCssPropertyInSelector {
        if (selectors.containsKey(propertyCodeItem.getSelectorClass())) {
            throw new DuplicateCssPropertyInSelector("Selector exist with key " + propertyCodeItem.getSelectorClass());
        }

        selectors.put(propertyCodeItem.getSelectorClass(),  propertyCodeItem);

    }

    public CssSelectorCodeItem getSelector(CssSelectorCodeItem propertyCodeItem) {


        return selectors.get(propertyCodeItem.getSelectorClass());

    }

    public Map<String, CssSelectorCodeItem> getSelectors() {
        return selectors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MediaQueryCodeItem)) return false;

        MediaQueryCodeItem that = (MediaQueryCodeItem) o;

        return mediaQuery.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {

        return mediaQuery.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + hashCode();
    }

    @Override
    public String getContent() {

        String result = "@media ";
        try {
            result += getValueForMedia();
        } catch (Exception e) {
            e.printStackTrace();
        }

        result += "{ \n";;
//        System.out.println("build ----------");
        for (Map.Entry<String, CssSelectorCodeItem> item :
                selectors.entrySet()) {
            CssSelectorCodeItem css = item.getValue();
//        System.out.println(item.getKey());
            result += css.getContent() + "\n";
        }

        result += "}\n";
        
        return result;
    }

    public String getValueForMedia() throws Exception {

        return mediaQuery.getFullValue();
//        return selector;
    }


}
