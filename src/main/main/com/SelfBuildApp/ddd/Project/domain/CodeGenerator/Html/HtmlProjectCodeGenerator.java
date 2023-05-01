package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlProjectCodeItem;
import com.SelfBuildApp.ddd.Project.domain.HtmlNode;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.TextNode;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class HtmlProjectCodeGenerator implements CodeGenerator<HtmlProject> {

    @Autowired
    private HtmlTagCodeGenerator tagGenerator;


    @Autowired
    private TextNodeCodeGenerator textGenerator;

    @Autowired
    private HtmlTagRepository htmlTagRepository;

    @Override
    public CodeGeneratedItem generate(HtmlProject arg) {

        HtmlProjectCodeItem htmlProjectCodeItem = new HtmlProjectCodeItem(arg);
        Set<HtmlNode> mainHtmlTagsForProject = arg.getItems();


        for (HtmlNode node: mainHtmlTagsForProject) {
            if (node instanceof HtmlTag) {
                htmlProjectCodeItem.addChild((HtmlNodeCodeItem) tagGenerator.generate((HtmlTag) node));

            } else if (node instanceof HtmlTag) {
                htmlProjectCodeItem.addChild((HtmlNodeCodeItem) textGenerator.generate((TextNode) node));
            }

        }
        
        return htmlProjectCodeItem;
    }
}
