package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssSelectorCodeItem;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CssStyleCodeGenerator implements CodeGenerator<HtmlProject> {

    @Autowired
    private HtmlTagRepository htmlTagRepository;

    @Override
    public CodeGeneratedItem generate(HtmlProject arg) {

        CssSelectorCodeItem cssSelectorCodeItem = new CssSelectorCodeItem();
//        htmlTagRepository.findMainHtmlTagsForProject()
//        for (:
//             ) {
//
//        }

        return null;
    }
}
