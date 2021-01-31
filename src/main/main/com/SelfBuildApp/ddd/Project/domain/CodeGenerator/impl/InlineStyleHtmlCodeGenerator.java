package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.impl;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.HtmlProjectCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.InlineStyleHtmlProjectCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class InlineStyleHtmlCodeGenerator implements CodeGenerator<HtmlProject> {

    @Autowired
    protected InlineStyleHtmlProjectCodeGenerator projectCodeGenerator;

    @Override
    @Transactional
    public CodeGeneratedItem generate(HtmlProject arg) {
        return projectCodeGenerator.generate(arg);
    }
}
