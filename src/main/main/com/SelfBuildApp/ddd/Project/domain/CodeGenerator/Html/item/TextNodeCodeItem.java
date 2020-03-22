package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item;

import com.SelfBuildApp.ddd.Project.domain.TextNode;

public class TextNodeCodeItem extends HtmlNodeCodeItem {

    protected TextNode textNode;

    public TextNodeCodeItem(TextNode textNode) {
        this.textNode = textNode;
    }


    @Override
    public String getContent() {

        StringBuilder res = this.stringBuilder;
        if (this.stringBuilder == null) {
            res = new StringBuilder();

        }

        res.append(textNode.getText() + "\n");
        return res.toString();
    }


}
