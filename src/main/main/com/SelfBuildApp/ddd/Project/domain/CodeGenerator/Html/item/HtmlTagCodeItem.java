package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssSelectorCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Exception.DuplicateHtmlClass;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;

import java.util.*;

public class HtmlTagCodeItem extends HtmlNodeCodeItem {

    protected HtmlTag tag;
    private List<HtmlNodeCodeItem> children;
    protected List<String> classList;
    protected Map<String, CssSelectorCodeItem> selectorsClass;



    public HtmlTagCodeItem(HtmlTag tag) {
        this.tag = tag;
        classList = new ArrayList<>();
        children = new ArrayList<>();
        selectorsClass = new HashMap<>();
    }

    public boolean hasUUID(String uuid)
    {
        return tag.getId().equals(uuid);
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

    public void removeClass(String classArg) throws DuplicateHtmlClass {
        classArg = classArg.toLowerCase();
        if (!classList.contains(classArg)) {
            throw new DuplicateHtmlClass("Html class \"" + classArg + "\" not exist in tag with id " + tag.getId());
        }
        this.classList.remove(classArg);
    }

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
        this.appendClassToContent(res);
        this.closeOpenedTag(res);

        for (HtmlNodeCodeItem item : children) {
            res.append(item.getContent());
        }

        this.closeTagWithName(res);

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
    private void appendClassToContent(StringBuilder res)
    {
        if (selectorsClass.size() > 0) {
            res.append(" class=\"");

            for (Map.Entry<String, CssSelectorCodeItem> node : this.selectorsClass.entrySet()) {
                CssSelectorCodeItem selector = node.getValue();
                res.append(selector.getSelectorClass()).append(" ");
            }

            res.append("\"");
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
}
