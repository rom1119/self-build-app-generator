package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.Builder;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlNode;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlTagCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.TextNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.TextNode;

public class HtmlTreeBuilder {
    public HtmlNode build(com.SelfBuildApp.ddd.Project.domain.HtmlNode tag)
    {
        HtmlTagCodeItem codeItem = new HtmlTagCodeItem((HtmlTag) tag);


        return buildRecursive(tag, codeItem);
    }

    private HtmlNode buildRecursive(com.SelfBuildApp.ddd.Project.domain.HtmlNode tag, HtmlTagCodeItem parent)
    {
//        HtmlTagCodeItem codeItem = new HtmlTagCodeItem(this.tag);
        HtmlNode codeItemChildren = null;

        if (tag instanceof HtmlTag) {
            codeItemChildren = new HtmlTagCodeItem((HtmlTag) tag);

            for (com.SelfBuildApp.ddd.Project.domain.HtmlNode item: ((HtmlTag) tag).getChildren()) {

                buildRecursive(item, (HtmlTagCodeItem) codeItemChildren);

            }

        } else if (tag instanceof TextNode) {
            codeItemChildren = new TextNodeCodeItem((TextNode) tag);

        }

        parent.addChild(codeItemChildren);
//        codeItem


        return codeItemChildren;
    }
}
