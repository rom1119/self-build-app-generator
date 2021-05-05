package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;

import java.util.*;

public class CssProjectCodeItem implements CodeGeneratedItem {

    protected HtmlProject htmlProject;
    private Map<String, CssSelectorCodeItem> cssSelectors;
    private Map<Long, MediaQueryCodeItem> mediaQueries;
    private Map<Long, FontFaceCodeItem> fontFaces;
    private Map<String, KeyFrameCodeItem> keyFrames;

    public CssProjectCodeItem(HtmlProject htmlProject) {
        this.htmlProject = htmlProject;
        this.cssSelectors = new HashMap<>();
        this.mediaQueries = new HashMap<>();
        this.fontFaces = new HashMap<>();
        this.keyFrames = new HashMap<>();
    }

    public String getProjectId()
    {
        return htmlProject.getId();
    }

    public void addMediaQuery(MediaQueryCodeItem item)
    {
        mediaQueries.put(item.mediaQuery.getId(), item);
    }

    public void addFontFace(FontFaceCodeItem item)
    {
        fontFaces.put(item.fontFace.getId(), item);
    }

    public void addKeyFrame(KeyFrameCodeItem item)
    {
        keyFrames.put(item.keyFrame.getId(), item);
    }

    public MediaQueryCodeItem getMediaQuery(Long key)
    {
        return mediaQueries.get(key);
    }

    public Map<Long, MediaQueryCodeItem> getMediaQueries()
    {
        return mediaQueries;
    }


    public void addSelector(CssSelectorCodeItem item)
    {
        System.out.println("add-chi");
        System.out.println(cssSelectors.size());
        cssSelectors.put(item.getSelectorClass(), item);
        System.out.println(cssSelectors.size());
        System.out.println("end-add-chi");
    }

    @Override
    public String getContent() {

        StringBuilder res = new StringBuilder();

//        this.openTagWithWithDoctype(res);
//        this.appendSectionHead(res);
//        this.openBody(res);

        for (Map.Entry<String, CssSelectorCodeItem> node : this.cssSelectors.entrySet()) {
            CssSelectorCodeItem value = node.getValue();
            res
                .append(value.getContent());
        }

        for (Map.Entry<Long, MediaQueryCodeItem> node : this.mediaQueries.entrySet()) {
            MediaQueryCodeItem value = node.getValue();
            res
                .append(value.getContent());
        }

        for (Map.Entry<Long, FontFaceCodeItem> node : this.fontFaces.entrySet()) {
            FontFaceCodeItem value = node.getValue();
            res
                .append(value.getContent());
        }

        for (Map.Entry<String, KeyFrameCodeItem> node : this.keyFrames.entrySet()) {
            KeyFrameCodeItem value = node.getValue();
            res
                .append(value.getContent());
        }

//        this.closeBodyWithHtml(res);

        return res.toString();
    }

    public CssSelectorCodeItem getSelectorByHexString(String key) throws Exception {
        long s = Long.parseLong(key, 16);

        for (Map.Entry<String, CssSelectorCodeItem> node : this.cssSelectors.entrySet()) {
            CssSelectorCodeItem selector = node.getValue();
            System.out.println(selector.hashCode());
            System.out.println(key);
            System.out.println(s);
            System.out.println("SSSSS");
            if (selector.hashCode() == s) {
                throw new Exception("działa :)"  + key + " : " + s);
            }
        }

        return null;
    }

    public void removeSelector(CssSelectorCodeItem selectorCodeItem)
    {
//        if (children.containsKey(selectorCodeItem.getSelectorClass())) {
            cssSelectors.remove(selectorCodeItem.getSelectorClass());
            System.out.println("remove selector");
//        }

    }

    public boolean hasOneSelector() {

        return cssSelectors.size() == 1;
    }
    public CssSelectorCodeItem getFirstSelector() {
        if (cssSelectors.entrySet().iterator().hasNext()) {
            return cssSelectors.entrySet().iterator().next().getValue();
        }
        return null;
    }

    public void updateSelectorWithKey(String oldSelectorKey, CssSelectorCodeItem selector2) {

        cssSelectors.remove(oldSelectorKey);
//        System.out.println(children.size());
        addSelector(selector2);
//        System.out.println(children.size());
//        System.out.println("end-remove");
    }

}
