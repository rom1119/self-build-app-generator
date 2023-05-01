package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.impl;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.*;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateCssPropertyInSelector;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlTagCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.TextNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.FontFace;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssStyleRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.FontFaceRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.PseudoSelectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FontFaceGenerator implements CodeGenerator<CssProjectCodeItem> {

    @Autowired
    private FontFaceRepository repository;

    @Autowired
    private PseudoSelectorRepository pseudoSelectorRepository;

//    private CssProjectCodeItem projectCodeItem;

    @Autowired
    private PathFileManager pathFileManager;

    protected List<HtmlNodeCodeItem> tagsCodeItem;
    protected List<FontFace> fontFaceList;

    public void setTagsCodeItem(List<HtmlNodeCodeItem> tagsCodeItem) {
        this.tagsCodeItem = tagsCodeItem;
    }

    public void setFontFaceList(List<FontFace> fontFaceList) {
        this.fontFaceList = fontFaceList;
    }

    //    public void setProjectCodeItem(CssProjectCodeItem projectCodeItem) {
//        this.projectCodeItem = projectCodeItem;
//    }

    @Override
    public CodeGeneratedItem generate(CssProjectCodeItem arg) {




        List<FontFace> allFontFaceList = this.fontFaceList;

        for (FontFace fontFace : allFontFaceList) {
            fontFace.setPathFileManager(pathFileManager);
//            MediaQueryCodeItem mediaQueryCodeItem = arg.getMediaQuery(fontFace.getMediaQuery().getId());
//
//            if (mediaQueryCodeItem == null) {
            FontFaceCodeItem mediaQueryCodeItem = new FontFaceCodeItem(fontFace);
            arg.addFontFace(mediaQueryCodeItem);
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
