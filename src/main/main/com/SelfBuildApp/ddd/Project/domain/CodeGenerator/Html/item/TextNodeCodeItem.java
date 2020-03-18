package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateHtmlClass;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.TextNode;

import java.util.ArrayList;
import java.util.List;

public class TextNodeCodeItem extends HtmlNode implements CodeGeneratedItem {

    protected TextNode textNode;

    public TextNodeCodeItem(TextNode textNode) {
        this.textNode = textNode;
    }


    @Override
    public String getContent() {

        return textNode.getText();
    }


}
