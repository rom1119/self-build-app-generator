package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.Css.KeyFrame.KeyFrameSelector;

public class KeyFrameCodeItem implements CodeGeneratedItem {

    protected KeyFrame keyFrame;

    public KeyFrameCodeItem(KeyFrame mediaQuery) {
        this.keyFrame = mediaQuery;
//        selectors = new HashMap<>();
//        cssProperties.put(css.getKey(), css);
//        this.reChangeSelector();
    }

    public String getKeyFrameId()
    {
        return  keyFrame.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyFrameCodeItem)) return false;

        KeyFrameCodeItem that = (KeyFrameCodeItem) o;

        return keyFrame.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {

        return keyFrame.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + hashCode();
    }

    @Override
    public String getContent() {

        String result = "@keyframes ";

        result += keyFrame.getName();
        result += "{ \n";
//        System.out.println("build ----------");
        for (int i = 0; i < keyFrame.getSelectorList().size(); i++) {
            PseudoSelector selector = keyFrame.getSelectorList().get(i);
            KeyFrameSelector frameSelector = KeyFrameSelector.Factory.create(selector);
//            result += css.getContent() + "\n";

            result += frameSelector.getValue() + " { \n";

            for (CssStyle css :selector.getCssStyleList()) {
                try {
                    CssPropertyCodeItem cssPropertyCodeItem = new CssPropertyCodeItem(css);
                    result += cssPropertyCodeItem.getContent() + "\n";

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            result += "url(\"" + url + "\")";
//
//            if (srcItem.getFileName() != null) {
//                result += " ";
//                result += "format(\"" + srcItem.getFormat() + "\")";
//            }
//
//            if (i == keyFrame.getSrc().size() - 1) {
//                result += " ;\n";
//            } else {
//                result += " ,\n";
//            }
            result += "}\n";
        }

        result += "}\n";

        return result;
    }


}
