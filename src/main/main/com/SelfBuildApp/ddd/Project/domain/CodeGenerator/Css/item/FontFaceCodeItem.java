package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item;

import com.SelfBuildApp.ddd.Project.domain.AssetProject;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.CodeGeneratedItem;
import com.SelfBuildApp.ddd.Project.domain.FontFace;

import java.util.HashMap;
import java.util.Map;

public class FontFaceCodeItem implements CodeGeneratedItem {

    protected FontFace fontFace;

    public FontFaceCodeItem(FontFace mediaQuery) {
        this.fontFace = mediaQuery;
//        selectors = new HashMap<>();
//        cssProperties.put(css.getKey(), css);
//        this.reChangeSelector();
    }

    public Long getFontFaceId()
    {
        return  fontFace.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FontFaceCodeItem)) return false;

        FontFaceCodeItem that = (FontFaceCodeItem) o;

        return fontFace.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {

        return fontFace.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + hashCode();
    }

    @Override
    public String getContent() {

        String result = "@font-face ";


        result += "{ \n";
        result += "font-family: \""+ fontFace.getName() + "\"; \n";
        result += "src: ";
//        System.out.println("build ----------");
        for (int i = 0; i < fontFace.getSrc().size(); i++) {
            AssetProject srcItem = fontFace.getSrc().get(i);
//            result += css.getContent() + "\n";

            String url = "";
            try {
                if (srcItem.getResourcePath() != null){
                    url = srcItem.getResourcePath();
                } else {
                    url = srcItem.getResourceUrl();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            result += "url(\"" + url + "\")";

            if (srcItem.getFileName() != null) {
                result += " ";
                result += "format(\"" + srcItem.getFormat() + "\")";
            }

            if (i == fontFace.getSrc().size() - 1) {
                result += " ;\n";
            } else {
                result += " ,\n";
            }
        }

        result += "}\n";
        
        return result;
    }


}
