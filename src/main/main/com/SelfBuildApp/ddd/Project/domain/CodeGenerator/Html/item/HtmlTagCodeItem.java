package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateHtmlClass;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.TextNode;

import java.util.ArrayList;
import java.util.List;

public class HtmlTagCodeItem extends HtmlNode implements CodeGeneratedItem {

    protected HtmlTag tag;
    private List<HtmlNode> children;
    protected List<String> classList;


    public HtmlTagCodeItem(HtmlTag tag) {
        this.tag = tag;
        classList = new ArrayList<>();
        children = new ArrayList<>();
    }

    public void addClass(String classArg) throws DuplicateHtmlClass {
        classArg = classArg.toLowerCase();
        if (classList.contains(classArg)) {
            throw new DuplicateHtmlClass("Html class \"" + classArg + "\" exist in tag with id " + tag.getId());
        }
        this.classList.add(classArg);
    }

    public void addChild(HtmlNode item)
    {
        children.add(item);
    }

    @Override
    public String getContent() {
        StringBuilder res = new StringBuilder();

        this.openTagWithName(res);
        this.appendClassToContent(res);
        this.closeOpenedTag(res);


        this.closeTagWithName(res);

        return res.toString();
    }

    private void openTagWithName(StringBuilder res)
    {
        res.append("<")
            .append(this.tag.getTagName());
    }

    private void closeTagWithName(StringBuilder res)
    {
        res.append("</")
            .append(this.tag.getTagName())
            .append(">");
    }

    private void closeOpenedTag(StringBuilder res)
    {
        res.append(">");
    }
    private void appendClassToContent(StringBuilder res)
    {
        if (classList.size() > 0) {
            res.append("class=\"");

            for (String classEl: this.classList){
                res.append(classEl).append(" ");
            }

            res.append("\"");
        }

    }


}
