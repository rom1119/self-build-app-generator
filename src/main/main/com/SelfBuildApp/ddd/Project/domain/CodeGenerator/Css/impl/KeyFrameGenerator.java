package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.impl;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.*;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateCssPropertyInSelector;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlTagCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.TextNodeCodeItem;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.FontFaceRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.KeyFrameRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.PseudoSelectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KeyFrameGenerator implements CodeGenerator<CssProjectCodeItem> {

    @Autowired
    private KeyFrameRepository repository;

    @Autowired
    private PseudoSelectorRepository pseudoSelectorRepository;

//    private CssProjectCodeItem projectCodeItem;

    @Autowired
    private PathFileManager pathFileManager;

    protected List<HtmlNodeCodeItem> tagsCodeItem;

    public void setTagsCodeItem(List<HtmlNodeCodeItem> tagsCodeItem) {
        this.tagsCodeItem = tagsCodeItem;
    }


//    public void setProjectCodeItem(CssProjectCodeItem projectCodeItem) {
//        this.projectCodeItem = projectCodeItem;
//    }

    @Override
    public CodeGeneratedItem generate(CssProjectCodeItem arg) {




        List<KeyFrame> keyFrames = repository.findAllForProjectId(arg.getProjectId());

        for (KeyFrame fontFace : keyFrames) {
            fontFace.setPathFileManager(pathFileManager);
//            MediaQueryCodeItem mediaQueryCodeItem = arg.getMediaQuery(fontFace.getMediaQuery().getId());
//
//            if (mediaQueryCodeItem == null) {
            KeyFrameCodeItem mediaQueryCodeItem = new KeyFrameCodeItem(fontFace);
            arg.addKeyFrame(mediaQueryCodeItem);
//            }

//            if (fontFace.getPseudoSelector() != null) {
//
//                CssSelectorCodeItem selectorCodeItem = addPseudoSelectorToProject(mediaQueryCodeItem, fontFace.getPseudoSelector());
//                try {
//                    mediaQueryCodeItem.addSelector(selectorCodeItem);
//                } catch (DuplicateCssPropertyInSelector duplicateCssPropertyInSelector) {
//                    duplicateCssPropertyInSelector.printStackTrace();
//                }
//            }

        }

        return null;
    }

}
