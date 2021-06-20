package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssSelectorCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.MediaQueryCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateHtmlClass;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.HtmlTagAttr;

import java.util.*;

public class HtmlTagCodeItem extends HtmlNodeCodeItem {

    protected HtmlTag tag;
    private List<HtmlNodeCodeItem> children;
    protected List<String> classList;
    protected Map<String, CssSelectorCodeItem> selectorsClass;
    protected Map<Long, Map<String, CssSelectorCodeItem>> selectorsClassForMediaQueries;

    protected boolean inlineStyle = false;


    public HtmlTagCodeItem(HtmlTag tag) {
        this.tag = tag;
        classList = new ArrayList<>();
        children = new ArrayList<>();
        selectorsClass = new HashMap<>();
        selectorsClassForMediaQueries = new HashMap<>();
    }

    public void setInlineStyle(boolean inlineStyle) {
        this.inlineStyle = inlineStyle;
    }

    public boolean hasUUID(String uuid)
    {
        return tag.getId().equals(uuid);
    }

    public boolean hasShortUUID(String shortUuid)
    {
        return tag.getShortUuid().equals(shortUuid);
    }


    public void addClass(String classArg) throws DuplicateHtmlClass {
        classArg = classArg.toLowerCase();
        if (!classList.contains(classArg)) {
            this.classList.add(classArg);
//            throw new DuplicateHtmlClass("Html class \"" + classArg + "\" exist in tag with id " + tag.getId());
        }
    }

    public void addSelector(CssSelectorCodeItem selectorCodeItem) {
//        System.out.println(this.selectorsClass.size());
        this.selectorsClass.put(selectorCodeItem.getSelector(), selectorCodeItem);
//        System.out.println(this.selectorsClass.size());
//        if (!classList.contains(classArg)) {
////            throw new DuplicateHtmlClass("Html class \"" + classArg + "\" exist in tag with id " + tag.getId());
//        }
    }

    public void addSelectorToMediaQuery(MediaQueryCodeItem codeItem, CssSelectorCodeItem selectorCodeItem) {
//        System.out.println(this.selectorsClass.size());
        if (this.selectorsClassForMediaQueries.get(codeItem.getMediaQueryId()) != null) {
            this.selectorsClassForMediaQueries.get(codeItem.getMediaQueryId()).put(selectorCodeItem.getSelector(), selectorCodeItem);

        }
//        System.out.println(this.selectorsClass.size());
//        if (!classList.contains(classArg)) {
////            throw new DuplicateHtmlClass("Html class \"" + classArg + "\" exist in tag with id " + tag.getId());
//        }
    }
    public void addMediaQuery(MediaQueryCodeItem codeItem) {
//        System.out.println(this.selectorsClass.size());
        if (this.selectorsClassForMediaQueries.get(codeItem.getMediaQueryId()) == null) {
            this.selectorsClassForMediaQueries.put(codeItem.getMediaQueryId(), new HashMap<String, CssSelectorCodeItem>());

        }
//        System.out.println(this.selectorsClass.size());
//        if (!classList.contains(classArg)) {
////            throw new DuplicateHtmlClass("Html class \"" + classArg + "\" exist in tag with id " + tag.getId());
//        }
    }

//    public Map<String, CssSelectorCodeItem> getSelectors() {
//        return selectorsClass;
//    }

//    public void removeClass(String classArg) throws DuplicateHtmlClass {
//        classArg = classArg.toLowerCase();
//        if (!classList.contains(classArg)) {
//            throw new DuplicateHtmlClass("Html class \"" + classArg + "\" not exist in tag with id " + tag.getId());
//        }
//        this.classList.remove(classArg);
//    }

    public void addChild(HtmlNodeCodeItem item)
    {
        children.add(item);
    }

    public List<HtmlNodeCodeItem> getChildren() {
        return children;
    }

    @Override
    public String getContent() {
        StringBuilder res = this.stringBuilder;
        if (this.stringBuilder == null) {
            res = new StringBuilder();

        }
        this.openTagWithName(res);
        this.appendAttrsToContent(res);
        this.appendClassToContent(res);
        if (inlineStyle == true) {
            this.appendStylesToContent(res);

        }

        if (this.tag.isClosingTag()){

            this.closeOpenedTag(res);

            for (HtmlNodeCodeItem item : children) {
                res.append(item.getContent());
            }

            this.closeTagWithName(res);
        } else {
            this.shortCloseTag(res);
        }


        return res.toString();
    }

    private void openTagWithName(StringBuilder res)
    {
        res.append("<")
            .append(this.tag.getTagName());
    }

    private void closeTagWithName(StringBuilder res)
    {
        res.append("</")
            .append(this.tag.getTagName())
            .append(">\n");
    }

    private void closeOpenedTag(StringBuilder res)
    {
        res.append(">\n");
    }

    private void shortCloseTag(StringBuilder res)
    {
        res.append("/>\n");
    }
    private void appendStylesToContent(StringBuilder res)
    {
        if (tag.getCssStyleList().size() > 0) {
            res.append(" style=\"");

            for (CssStyle css : tag.getCssStyleList()) {
                if (css.getMediaQuery() != null) {
                    continue;
                }
                try {
                    res
                        .append(css.getName() + ": " + css.getFullValue())
                        .append("; ");
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            res.append("\"");
        }
    }

    private void appendClassToContent(StringBuilder res)
    {
        if (selectorsClass.size() > 0 || this.selectorsClassForMediaQueries.size() > 0) {
            res.append(" class=\"");

            for (Map.Entry<String, CssSelectorCodeItem> node : this.selectorsClass.entrySet()) {
                CssSelectorCodeItem selector = node.getValue();
                res.append(selector.getSelectorForHtmlClass()).append(" ");
            }
            for (Map.Entry<Long, Map<String, CssSelectorCodeItem>> mediaQ : this.selectorsClassForMediaQueries.entrySet()) {
                for (Map.Entry<String, CssSelectorCodeItem> selEl : mediaQ.getValue().entrySet()) {

                    CssSelectorCodeItem selectorMedia = selEl.getValue();
                    res.append(selectorMedia.getSelectorForHtmlClass()).append(" ");
                }
            }

            res.append("\"");
        }

    }

    private void appendAttrsToContent(StringBuilder res)
    {
        if (tag.getAttrs().size() > 0) {
            for (Map.Entry<String, HtmlTagAttr> node : tag.getAttrs().entrySet()) {
                HtmlTagAttr attr = node.getValue();
                res.append(" ");
                res.append(attr.key);
                res.append("=\"");
                res.append(attr.value);
                res.append("\"");
            }
        }

    }


    public CssSelectorCodeItem getSelector() {
        if (selectorsClass.entrySet().iterator().hasNext()) {
            return selectorsClass.entrySet().iterator().next().getValue();
        }
        return null;
    }

    public CssSelectorCodeItem getOwnerSelector() {
        for (Map.Entry<String, CssSelectorCodeItem> el: selectorsClass.entrySet()){
            CssSelectorCodeItem selectorCodeItem = el.getValue();

            if (selectorCodeItem.isOwnerTag()) {
                return selectorCodeItem;
            }
        }
        return null;
    }

    public CssSelectorCodeItem getOwnerSelectorForMediaQuery(MediaQueryCodeItem projectCodeItem) {
        Map<String, CssSelectorCodeItem> listSelectors = selectorsClassForMediaQueries.get(projectCodeItem.getMediaQueryId());

        if (listSelectors == null){
            return null;

        }

        for (Map.Entry<String, CssSelectorCodeItem> el: listSelectors.entrySet()){
            CssSelectorCodeItem selectorCodeItem = el.getValue();

            if (selectorCodeItem.isOwnerTag()) {
                return selectorCodeItem;
            }
        }

        return null;
    }
}
