package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateHtmlClass;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;

import java.util.ArrayList;
import java.util.List;

public class HtmlProjectCodeItem extends HtmlNodeCodeItem implements CodeGeneratedItem {

    protected HtmlProject htmlProject;
    private List<HtmlNodeCodeItem> children;
//    protected List<String> classList;


    public HtmlProjectCodeItem(HtmlProject tag) {
        this.htmlProject = tag;
//        classList = new ArrayList<>();
        children = new ArrayList<>();
    }

    public void addChild(HtmlNodeCodeItem item)
    {
        children.add(item);
    }

    public List<HtmlNodeCodeItem> getChildren() {
        return children;
    }

    @Override
    public String getContent() {
        StringBuilder res = new StringBuilder();

        this.openTagWithWithDoctype(res);
        this.appendSectionHead(res);
        this.openBody(res);

        for (HtmlNodeCodeItem node : this.children) {
            res.append(node.getContent());
        }

        this.closeBodyWithHtml(res);

        return res.toString();
    }

    private void openTagWithWithDoctype(StringBuilder res)
    {
        res.append("<!DOCTYPE>")
            .append("<html>");
    }

    private void openBody(StringBuilder res)
    {
        res
            .append("<body>\n");
    }

    private void closeBodyWithHtml(StringBuilder res)
    {
        res
            .append("</body>")
            .append("</html>");
    }

    private void appendSectionHead(StringBuilder res)
    {
        res.append("<head>")
                .append("<title>")
                    .append(this.htmlProject.getName())
                .append("</title>")
            .append("</head>");
    }

}
