package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateCssPropertyInSelector;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import org.apache.commons.codec.binary.Hex;

import java.util.*;

public class CssProjectCodeItem implements CodeGeneratedItem {

    protected HtmlProject htmlProject;
    private Map<String, CssSelectorCodeItem> children;

    public CssProjectCodeItem(HtmlProject htmlProject) {
        this.htmlProject = htmlProject;
        this.children = new HashMap<>();
    }

    public void addChild(CssSelectorCodeItem item)
    {
        System.out.println("add-chi");
        System.out.println(children.size());
        children.put(item.getSelectorClass(), item);
        System.out.println(children.size());
        System.out.println("end-add-chi");
    }

    @Override
    public String getContent() {

        StringBuilder res = new StringBuilder();

//        this.openTagWithWithDoctype(res);
//        this.appendSectionHead(res);
//        this.openBody(res);

        for (Map.Entry<String, CssSelectorCodeItem> node : this.children.entrySet()) {
            CssSelectorCodeItem value = node.getValue();
            res
                .append(value.getContent());
//                .append("\n");
        }

//        this.closeBodyWithHtml(res);

        return res.toString();
    }

    public CssSelectorCodeItem getSelectorByHexString(String key) throws Exception {
        long s = Long.parseLong(key, 16);

        for (Map.Entry<String, CssSelectorCodeItem> node : this.children.entrySet()) {
            CssSelectorCodeItem selector = node.getValue();
            System.out.println(selector.hashCode());
            System.out.println(key);
            System.out.println(s);
            System.out.println("SSSSS");
            if (selector.hashCode() == s) {
                throw new Exception("działa :)"  + key + " : " + s);
            }
        }

        return null;
    }

    public void removeSelector(CssSelectorCodeItem selectorCodeItem)
    {
//        if (children.containsKey(selectorCodeItem.getSelectorClass())) {
            children.remove(selectorCodeItem.getSelectorClass());
            System.out.println("remove selector");
//        }

    }

    public boolean hasOneSelector() {

        return children.size() == 1;
    }
    public CssSelectorCodeItem getFirstSelector() {
        if (children.entrySet().iterator().hasNext()) {
            return children.entrySet().iterator().next().getValue();
        }
        return null;
    }

    public void updateSelectorWithKey(String oldSelectorKey, CssSelectorCodeItem selector2) {
        System.out.println("remove");
        System.out.println(oldSelectorKey);
        System.out.println(selector2.getSelector());
        System.out.println(children.size());
        children.remove(oldSelectorKey);
        System.out.println(children.size());
        addChild(selector2);
        System.out.println(children.size());
        System.out.println("end-remove");
    }

//    public boolean hasSelector(CssSelectorCodeItem selector) {
//        System.out.println("contains");
//        System.out.println(selector);
//        System.out.println(selector.hashCode());
//        System.out.println(children.(selector));
////        for (CssSelectorCodeItem selector :
////                children) {
////
////        }
//        return children.contains(selector);
//    }
}
