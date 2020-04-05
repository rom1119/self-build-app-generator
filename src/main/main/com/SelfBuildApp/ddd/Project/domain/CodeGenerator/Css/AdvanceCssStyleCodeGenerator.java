package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGenerator;
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
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssStyleRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    protected List<HtmlNodeCodeItem> tagsCodeItem;

    public void setTagsCodeItem(List<HtmlNodeCodeItem> tagsCodeItem) {
        this.tagsCodeItem = tagsCodeItem;
    }

    @Override
    public CodeGeneratedItem generate(HtmlProject arg) {

        CssProjectCodeItem projectCodeItem = new CssProjectCodeItem(arg);
        List<CssStyle> allForProjectId = cssStyleRepository.findAllForProjectId(arg.getId());

        Map<String, List<HtmlTag>> uniqueStyles = uniqueStyles(allForProjectId);
        for (Map.Entry<String, List<HtmlTag>> el : uniqueStyles.entrySet()) {

            CssStyle css = cssStyleRepository.findOneByCssIdentity(el.getKey());

            if (el.getValue().size() == 1) {

                CssSelectorCodeItem selector = findSelector(projectCodeItem, el.getKey());

                if (selector == null) {
                    selector = new CssSelectorCodeItem(el.getKey());
                }

                CssPropertyCodeItem property = createProperty(css);
                selector = this.addPropertyToSelector(property, selector);



//                if (!projectCodeItem) {
//
//                }
                projectCodeItem.addChild(selector);
                for (HtmlTag tag : el.getValue()) {
                    try {
                        System.out.println(css.getName());
                        HtmlTagCodeItem tagCodeItem = getTagCodeItemByUUID(tag.getId());
                        tagCodeItem.addClass(selector.getSelector());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {

                CssSelectorCodeItem selector = findSelector(projectCodeItem, el.getKey());

                if (selector == null) {
                    selector = new CssSelectorCodeItem(el.getKey());
                }

                CssPropertyCodeItem property = createProperty(css);
                selector = this.addPropertyToSelector(property, selector);

            }

        }

        int a  = 0;

        return projectCodeItem;
    }

    private CssSelectorCodeItem findSelector(CssProjectCodeItem projectCodeItem, String identity)
    {
        CssSelectorCodeItem selector = null;

        try {
            selector = projectCodeItem.getSelectorByHexString(identity);
        } catch (Exception e) {
            e.printStackTrace();
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
