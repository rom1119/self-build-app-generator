package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.ValueGenerator;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Project.domain.Unit.BaseUnit;

public class FontFamilyValue extends ValueGenerator {

    public String generateValue(CssStyle cssStyle) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(buildFromMultipleValue(cssStyle));

        return  stringBuilder.toString();
    }




    protected String buildFromMultipleValue(CssStyle cssStyle) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int length = cssStyle.getCssValues().size();
        boolean commaToAddAfterDirection = false;
        for (CssValue cssValue : cssStyle.getCssValues()) {
            i++;

            if(commaToAddAfterDirection) {
                stringBuilder.append(", ");
                commaToAddAfterDirection = false;
            }

            if (cssValue.getUnitName() != null && !cssValue.getUnitName().isEmpty()) {

                stringBuilder.append("\"");
                BaseUnit firstUnit = getUnitFromNameAndValue(cssValue.getUnitName(), cssValue.getValue());
                stringBuilder.append(firstUnit.getValue());
                stringBuilder.append("\"");
            }



            if (i < length) {
                stringBuilder.append(", ");
            }
        }
//        stringBuilder.append("; ");


        return stringBuilder.toString();

    }
}
