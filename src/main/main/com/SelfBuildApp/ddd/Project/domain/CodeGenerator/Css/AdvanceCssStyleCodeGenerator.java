package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.impl.FontFaceGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.impl.KeyFrameGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.impl.MediaQueryGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.MediaQueryCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssProjectCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssPropertyCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssSelectorCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateCssPropertyInSelector;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlTagCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.TextNodeCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssStyleRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.PseudoSelectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AdvanceCssStyleCodeGenerator implements CodeGenerator<HtmlProject> {

    @Autowired
    private HtmlTagRepository htmlTagRepository;

    @Autowired
    private CssStyleRepository cssStyleRepository;

    @Autowired
    private PseudoSelectorRepository pseudoSelectorRepository;

    @Autowired
    private PathFileManager pathFileManager;

    @Autowired
    private MediaQueryGenerator mediaQueryGenerator;

    @Autowired
    private FontFaceGenerator fontFaceGenerator;

    @Autowired
    private KeyFrameGenerator keyFrameGenerator;

    protected List<HtmlNodeCodeItem> tagsCodeItem;

    public void setTagsCodeItem(List<HtmlNodeCodeItem> tagsCodeItem) {
        this.tagsCodeItem = tagsCodeItem;
    }



    @Override
    @Transactional
    public CodeGeneratedItem generate(HtmlProject arg) {

        CssProjectCodeItem projectCodeItem = new CssProjectCodeItem(arg);

        List<CssStyle> allForProjectId = cssStyleRepository.findAllForProjectId(arg.getId());

        Map<String, List<HtmlTag>> uniqueStyles = uniqueStyles(allForProjectId);
        this.addStylesToProject(projectCodeItem, uniqueStyles);


        List<PseudoSelector> allPseudoSelectors = pseudoSelectorRepository.findAllForProjectId(projectCodeItem.getProjectId());

        for (PseudoSelector pseudoSelector : allPseudoSelectors) {

            CssSelectorCodeItem selectorCodeItem = addPseudoSelectorToProject(pseudoSelector);
            projectCodeItem.addSelector(selectorCodeItem);

        }

        mediaQueryGenerator.setTagsCodeItem(tagsCodeItem);

        mediaQueryGenerator.generate(projectCodeItem);


        fontFaceGenerator.generate(projectCodeItem);


        keyFrameGenerator.generate(projectCodeItem);





            int a  = 0;

        return projectCodeItem;
    }

    protected CssSelectorCodeItem addPseudoSelectorToProject( PseudoSelector pseudoSelector)
    {
        CssSelectorCodeItem sel = new CssSelectorCodeItem();
        pseudoSelector.setPathFileManager(pathFileManager);

        try {
            HtmlTagCodeItem tagCodeItem = getTagCodeItemByShortUUID(pseudoSelector.getOwner().getShortUuid());

            sel.setSelector(pseudoSelector.getOwner().getShortUuid());
            tagCodeItem.addSelector(sel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sel.setRechangeSelectorOnAddCss(false);

        for (CssStyle el : pseudoSelector.getCssStyleList()) {
            CssPropertyCodeItem propertyMany = createProperty(el);
            addPropertyToSelector(propertyMany, sel);
        }

        sel.setPseudoClass(pseudoSelector.getName());

        return sel;
    }



    protected CssSelectorCodeItem addStyleCssToProject(CssProjectCodeItem projectCodeItem, CssStyle css, List<HtmlTag> el)
    {
        CssSelectorCodeItem selector = null;
        if (el.size() == 1) {

            HtmlTag tag = el.iterator().next();
            HtmlTagCodeItem tagCodeItem = null;
            try {
                tagCodeItem = getTagCodeItemByUUID(tag.getId());
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
            selector = tagCodeItem.getOwnerSelector();
            CssPropertyCodeItem property = createProperty(css);
            String oldSelectorKey = null;

            if (selector == null) {
                selector = new CssSelectorCodeItem(true);
                tagCodeItem.addSelector(selector);
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
                projectCodeItem.addSelector(selector2);
            }


        } else {


            selector = new CssSelectorCodeItem();
            CssPropertyCodeItem propertyMany = createProperty(css);
            addPropertyToSelector(propertyMany, selector);

//
//                CssPropertyCodeItem property = createProperty(css);
//                selector = this.addPropertyToSelector(property, selector);
//
            projectCodeItem.addSelector(selector);
            for (HtmlTag tag : el) {
                try {
                    HtmlTagCodeItem tagCodeItem = getTagCodeItemByUUID(tag.getId());
                    tagCodeItem.addSelector(selector);
                    System.out.println(css.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return selector;
    }
    protected void addStylesToProject(CssProjectCodeItem projectCodeItem, Map<String, List<HtmlTag>> uniqueStyles)
    {
        for (Map.Entry<String, List<HtmlTag>> el : uniqueStyles.entrySet()) {

            CssStyle css = cssStyleRepository.findOneByCssIdentity(el.getKey());
            css.setPathFileManager(pathFileManager);

            this.addStyleCssToProject(projectCodeItem, css, el.getValue());

        }
    }

//    private CssSelectorCodeItem findSelector(CssProjectCodeItem projectCodeItem, String identity)
//    {
//        CssSelectorCodeItem selector = null;
//
//        try {
//            selector = projectCodeItem.getSelectorByHexString(identity);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return selector;
//    }
//
//    private CssSelectorCodeItem findSelectorByTagCodeItem(CssProjectCodeItem projectCodeItem, String identity)
//    {
//        CssSelectorCodeItem selector = null;
//
//        try {
//            selector = projectCodeItem.getSelectorByHexString(identity);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return selector;
//    }

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
