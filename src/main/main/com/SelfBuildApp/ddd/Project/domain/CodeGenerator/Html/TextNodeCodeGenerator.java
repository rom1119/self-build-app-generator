package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.Builder.HtmlTreeBuilder;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TextNodeCodeGenerator implements CodeGenerator<TextNode> {

    @Autowired
    private HtmlTreeBuilder htmlTreeBuilder;

    @Override
    public CodeGeneratedItem generate(TextNode arg) {
        return htmlTreeBuilder.build(arg);
    }
}
