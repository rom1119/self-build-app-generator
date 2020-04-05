package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateCssPropertyInSelector;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import org.apache.commons.codec.binary.Hex;

import java.util.*;

public class CssProjectCodeItem implements CodeGeneratedItem {

    protected HtmlProject htmlProject;
    private Set<CssSelectorCodeItem> children;

    public CssProjectCodeItem(HtmlProject htmlProject) {
        this.htmlProject = htmlProject;
        this.children = new HashSet<>();
    }

    public void addChild(CssSelectorCodeItem item)
    {
        children.add(item);
    }

    @Override
    public String getContent() {

        StringBuilder res = new StringBuilder();

//        this.openTagWithWithDoctype(res);
//        this.appendSectionHead(res);
//        this.openBody(res);

        for (CssSelectorCodeItem node : this.children) {
            res
                .append(node.getContent());
//                .append("\n");
        }

//        this.closeBodyWithHtml(res);

        return res.toString();
    }

    public CssSelectorCodeItem getSelectorByHexString(String key) throws Exception {
        long s = Long.parseLong(key, 16);

        for (CssSelectorCodeItem selector : children) {
            System.out.println(selector.hashCode());
            System.out.println(key);
            System.out.println(s);
            if (selector.hashCode() == s) {
                throw new Exception("działa :)"  + key + " : " + s);
            }
        }

        return null;
    }
}
