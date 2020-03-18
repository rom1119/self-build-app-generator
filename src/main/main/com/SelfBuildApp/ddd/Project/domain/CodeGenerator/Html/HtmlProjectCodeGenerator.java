package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;

public class HtmlProjectCodeGenerator implements CodeGenerator<HtmlProject> {

    private CodeGenerator<HtmlTag> tagGenerator;
    @Override
    public CodeGeneratedItem generate(HtmlProject arg) {

        return null;
    }
}
