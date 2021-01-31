package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.Builder;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlTagCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.TextNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.TextNode;
import org.springframework.stereotype.Component;

@Component
public class HtmlInlineStyleTreeBuilder {

    public HtmlNodeCodeItem build(com.SelfBuildApp.ddd.Project.domain.HtmlNode tag)
    {
        HtmlTagCodeItem codeItem = new HtmlTagCodeItem((HtmlTag) tag);


        return buildRecursive(tag, codeItem);
    }

    private HtmlNodeCodeItem buildRecursive(com.SelfBuildApp.ddd.Project.domain.HtmlNode tag, HtmlTagCodeItem parent)
    {
//        HtmlTagCodeItem codeItem = new HtmlTagCodeItem(this.tag);
        HtmlNodeCodeItem codeItemChildren = null;

        if (tag instanceof HtmlTag) {
            codeItemChildren = new HtmlTagCodeItem((HtmlTag) tag);
            ((HtmlTagCodeItem)codeItemChildren).setInlineStyle(true);
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
