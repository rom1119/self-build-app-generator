package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.impl;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssProjectCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssPropertyCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssSelectorCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.MediaQueryCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateCssPropertyInSelector;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlTagCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.TextNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.MediaQuery;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssStyleRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.PseudoSelectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MediaQueryGenerator implements CodeGenerator<CssProjectCodeItem> {

    @Autowired
    private CssStyleRepository cssStyleRepository;

    @Autowired
    private PseudoSelectorRepository pseudoSelectorRepository;

//    private CssProjectCodeItem projectCodeItem;

    @Autowired
    private PathFileManager pathFileManager;

    protected List<HtmlNodeCodeItem> tagsCodeItem;

    protected boolean inlineStyle = false;


    public void setTagsCodeItem(List<HtmlNodeCodeItem> tagsCodeItem) {
        this.tagsCodeItem = tagsCodeItem;
    }

    public void setInlineStyle(boolean inlineStyle) {
        this.inlineStyle = inlineStyle;
    }

    //    public void setProjectCodeItem(CssProjectCodeItem projectCodeItem) {
//        this.projectCodeItem = projectCodeItem;
//    }

    @Override
    public CodeGeneratedItem generate(CssProjectCodeItem arg) {




        List<CssStyle> cssForMediaQueries = cssStyleRepository.findAllForProjectIdWhereHasMediaQueryOrPseudoSelector(arg.getProjectId());
        Map<String, List<HtmlTag>> uniqueStylesForMedia = uniqueStyles(cssForMediaQueries);

        for (CssStyle cssForMediaQuery : cssForMediaQueries) {

            MediaQuery mediaQuery = null;
            if (cssForMediaQuery.getMediaQuery() != null) {
                mediaQuery = cssForMediaQuery.getMediaQuery();
            } else {
                mediaQuery = cssForMediaQuery.getPseudoSelector().getMediaQuery();

            }
            MediaQueryCodeItem mediaQueryCodeItem = arg.getMediaQuery(mediaQuery.getId());

            if (mediaQueryCodeItem == null) {
                mediaQueryCodeItem = new MediaQueryCodeItem(mediaQuery);
                arg.addMediaQuery(mediaQueryCodeItem);
            }

            if (cssForMediaQuery.getPseudoSelector() != null) {

                CssSelectorCodeItem selectorCodeItem = addPseudoSelectorToProject(mediaQueryCodeItem, cssForMediaQuery.getPseudoSelector());
                try {
                    mediaQueryCodeItem.addSelector(selectorCodeItem);
                } catch (DuplicateCssPropertyInSelector duplicateCssPropertyInSelector) {
                    duplicateCssPropertyInSelector.printStackTrace();
                }
            }

        }

        for (Map.Entry<Long, MediaQueryCodeItem> mediaQueryEl  : arg.getMediaQueries().entrySet()) {


            for (Map.Entry<String, List<HtmlTag>> el : uniqueStylesForMedia.entrySet()) {

                CssStyle css = cssStyleRepository.findOneByCssIdentity(el.getKey());
                css.setPathFileManager(pathFileManager);
                this.addStyleCssToMediaQuery(mediaQueryEl.getValue(), css, el.getValue());

            }

        }

        return null;
    }

    protected CssSelectorCodeItem addPseudoSelectorToProject(MediaQueryCodeItem projectCodeItem, PseudoSelector pseudoSelector)
    {

        CssSelectorCodeItem sel = null;

        try {
            HtmlTagCodeItem tagCodeItem = getTagCodeItemByShortUUID(pseudoSelector.getOwner().getShortUuid());

            sel = tagCodeItem.getOwnerSelectorForMediaQuery(projectCodeItem);;

            if (sel == null) {
                sel = new CssSelectorCodeItem();
                sel.setSelector(pseudoSelector.getOwner().getShortUuid());
            } else {
//                sel.setSelector(sel.getSelector());

            }
            tagCodeItem.addMediaQuery(projectCodeItem);
            tagCodeItem.addSelectorToMediaQuery(projectCodeItem, sel);

            pseudoSelector.setPathFileManager(pathFileManager);

        } catch (Exception e) {
            e.printStackTrace();
        }

        sel.setRechangeSelectorOnAddCss(false);

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
                    tagCodeItem.addSelectorToMediaQuery(projectCodeItem, selector);

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
            if (inlineStyle) {
                cssProp.setImportant(true);
            }
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

    private HtmlTagCodeItem getTagCodeItemByShortUUID(String shortUuid) throws Exception {

        HtmlTagCodeItem recursiveTagCodeItemByUUID = findRecursiveTagCodeItemByShortUUID(shortUuid, tagsCodeItem);
        if (recursiveTagCodeItemByUUID == null) {
            throw new Exception("Not found HtmlNodeCodeItem with UUID " + shortUuid);

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

    private HtmlTagCodeItem findRecursiveTagCodeItemByShortUUID(String shortUuid, List<HtmlNodeCodeItem> children)
    {
        for (HtmlNodeCodeItem item : children) {

            if (item instanceof TextNodeCodeItem) {
                continue;
            }
            HtmlTagCodeItem tagCodeItem = (HtmlTagCodeItem)item;

            if (tagCodeItem.hasShortUUID(shortUuid)) {
                return tagCodeItem;
            }

            HtmlTagCodeItem res = findRecursiveTagCodeItemByShortUUID(shortUuid, tagCodeItem.getChildren());
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
