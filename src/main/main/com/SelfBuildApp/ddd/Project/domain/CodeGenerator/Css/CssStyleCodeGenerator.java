package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssSelectorCodeItem;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;

public class CssStyleCodeGenerator implements CodeGenerator<HtmlProject> {
    @Override
    public CodeGeneratedItem generate(HtmlProject arg) {

        CssSelectorCodeItem cssSelectorCodeItem = new CssSelectorCodeItem();

        return null;
    }
}
