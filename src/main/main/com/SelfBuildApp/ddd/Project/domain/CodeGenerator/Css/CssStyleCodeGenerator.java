package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssSelectorCodeItem;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssStyleRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CssStyleCodeGenerator implements CodeGenerator<HtmlProject> {

    @Autowired
    private HtmlTagRepository htmlTagRepository;

    @Autowired
    private CssStyleRepository cssStyleRepository;

    @Override
    public CodeGeneratedItem generate(HtmlProject arg) {

        CssSelectorCodeItem cssSelectorCodeItem = new CssSelectorCodeItem();
        cssStyleRepository.findAllForProjectId(arg.getId());
//        for (:
//             ) {
//
//        }

        return null;
    }
}
