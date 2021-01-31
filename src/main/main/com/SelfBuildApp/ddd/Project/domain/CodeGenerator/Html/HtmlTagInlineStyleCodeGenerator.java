package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.Builder.HtmlInlineStyleTreeBuilder;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.Builder.HtmlTreeBuilder;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HtmlTagInlineStyleCodeGenerator implements CodeGenerator<HtmlTag> {

    @Autowired
    private HtmlInlineStyleTreeBuilder htmlTreeBuilder;

    @Override
    public CodeGeneratedItem generate(HtmlTag arg) {
        return htmlTreeBuilder.build(arg);
    }
}
