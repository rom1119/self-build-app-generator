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

    public void setTagsCodeItem(List<HtmlNodeCodeItem> tagsCodeItem) {
        this.tagsCodeItem = tagsCodeItem;
    }


//    public void setProjectCodeItem(CssProjectCodeItem projectCodeItem) {
//        this.projectCodeItem = projectCodeItem;
//    }

    @Override
    public CodeGeneratedItem generate(CssProjectCodeItem arg) {




        List<FontFace> allFontFaceList = repository.findAllForProjectId(arg.getProjectId());

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

    protected CssSelectorCodeItem addPseudoSelectorToProject(MediaQueryCodeItem projectCodeItem, PseudoSelector pseudoSelector)
    {

        CssSelectorCodeItem sel = null;

        try {
            HtmlTagCodeItem tagCodeItem = getTagCodeItemByUUID(pseudoSelector.getOwner().getId());

            sel = tagCodeItem.getOwnerSelectorForMediaQuery(projectCodeItem);;

            if (sel == null) {
                sel = new CssSelectorCodeItem();
                sel.setSelector(pseudoSelector.getOwner().getShortUuid());
            } else {
                sel.setSelector(sel.getSelector());

            }
            tagCodeItem.addMediaQuery(projectCodeItem);
            tagCodeItem.addSelectorToMediaQuery(projectCodeItem, sel);

            pseudoSelector.setPathFileManager(pathFileManager);

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (CssStyle el : pseudoSelector.getCssStyleList()) {
            String oldSelectorKey = sel.getSelector();
            CssPropertyCodeItem propertyMany = createProperty(el);
            addPropertyToSelector(propertyMany, sel);
//            projectCodeItem.updateSelectorWithKey(oldSelectorKey, sel);

        }


        sel.setPseudoClass(pseudoSelector.getName());

        return sel;
    }

    protected CssSelectorCodeItem addStyleCssToMediaQuery(MediaQueryCodeItem projectCodeItem, CssStyle css, List<HtmlTag> el)
    {
        CssSelectorCodeItem selector = null;
        if (el.size() == 1) {

            HtmlTag tag = el.iterator().next();
            HtmlTagCodeItem tagCodeItem = null;
            try {
                tagCodeItem = getTagCodeItemByUUID(tag.getId());
                tagCodeItem.addMediaQuery(projectCodeItem);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (tagCodeItem == null) {
                return null;
            }
//                System.out.println("start");
//
//                System.out.println(tagCodeItem);
//                System.out.println(selector);
            selector = tagCodeItem.getOwnerSelectorForMediaQuery(projectCodeItem);
            CssPropertyCodeItem property = createProperty(css);
            String oldSelectorKey = null;

            if (selector == null) {
                selector = new CssSelectorCodeItem(true);
            } else {
                oldSelectorKey = selector.getSelectorClass();
            }

            CssSelectorCodeItem selector2 = selector;
            this.addPropertyToSelector(property, selector);

//                System.out.println("START");
//                System.out.println("====================================================");
//                System.out.println(property.hashCode());
//                System.out.println(property.getKey());
//                System.out.println(property.getValue());

            if (oldSelectorKey != null) {
//                    System.out.println("ZZZZZZZ");
                projectCodeItem.updateSelectorWithKey(oldSelectorKey, selector2);
            } else {
//                    System.out.println("XXXXX");
                tagCodeItem.addSelectorToMediaQuery(projectCodeItem, selector);

                try {
                    projectCodeItem.addSelector(selector2);
                } catch (DuplicateCssPropertyInSelector duplicateCssPropertyInSelector) {
                    duplicateCssPropertyInSelector.printStackTrace();
                }
            }


        } else {


            selector = new CssSelectorCodeItem();
            CssPropertyCodeItem propertyMany = createProperty(css);
            addPropertyToSelector(propertyMany, selector);

//
//                CssPropertyCodeItem property = createProperty(css);
//                selector = this.addPropertyToSelector(property, selector);
//
            try {
                projectCodeItem.addSelector(selector);
            } catch (DuplicateCssPropertyInSelector duplicateCssPropertyInSelector) {
                duplicateCssPropertyInSelector.printStackTrace();
            }
            for (HtmlTag tag : el) {
                try {
                    HtmlTagCodeItem tagCodeItem = getTagCodeItemByUUID(tag.getId());
                    tagCodeItem.addMediaQuery(projectCodeItem);

                    try {
                        projectCodeItem.addSelector(selector);
                    } catch (DuplicateCssPropertyInSelector duplicateCssPropertyInSelector) {
                        duplicateCssPropertyInSelector.printStackTrace();
                    }
                    System.out.println(css.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return selector;
    }

    private CssSelectorCodeItem addPropertyToSelector(CssPropertyCodeItem cssProp, CssSelectorCodeItem selectorCodeItem)
    {

        try {
            selectorCodeItem.addProperty(cssProp);
        } catch (DuplicateCssPropertyInSelector duplicateCssPropertyInSelector) {
            duplicateCssPropertyInSelector.printStackTrace();
        }

        return selectorCodeItem;
    }

    private CssPropertyCodeItem createProperty(CssStyle css)
    {
        CssPropertyCodeItem cssPropertyCodeItem = null;
        try {
            cssPropertyCodeItem = new CssPropertyCodeItem(css);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cssPropertyCodeItem;
    }

    private HtmlTagCodeItem getTagCodeItemByUUID(String uuid) throws Exception {

        HtmlTagCodeItem recursiveTagCodeItemByUUID = findRecursiveTagCodeItemByUUID(uuid, tagsCodeItem);
        if (recursiveTagCodeItemByUUID == null) {
            throw new Exception("Not found HtmlNodeCodeItem with UUID " + uuid);

        }

        return recursiveTagCodeItemByUUID;
    }


    private HtmlTagCodeItem findRecursiveTagCodeItemByUUID(String uuid, List<HtmlNodeCodeItem> children)
    {
        for (HtmlNodeCodeItem item : children) {

            if (item instanceof TextNodeCodeItem) {
                continue;
            }
            HtmlTagCodeItem tagCodeItem = (HtmlTagCodeItem)item;

            if (tagCodeItem.hasUUID(uuid)) {
                return tagCodeItem;
            }

            HtmlTagCodeItem res = findRecursiveTagCodeItemByUUID(uuid, tagCodeItem.getChildren());
            if (res != null) {
                return res;
            }

        }

        return null;
    }

    private Map<String, List<HtmlTag>> uniqueStyles(List<CssStyle> styleList)
    {
        Map<String, List<HtmlTag>> res = new HashMap<>();

        for (CssStyle css : styleList) {
            if (css.getHtmlTag() == null) {
                continue;
            }
            if (res.containsKey(css.getCssIdentity())) {

                List<HtmlTag> el = res.get(css.getCssIdentity());
                el.add(css.getHtmlTag());

            } else {
                List<HtmlTag> list = new ArrayList<>();
                list.add(css.getHtmlTag());
                res.put(css.getCssIdentity(), list);
            }

//            System.out.println(css.getHtmlTag().getId());
        }

        return res;

    }
}
