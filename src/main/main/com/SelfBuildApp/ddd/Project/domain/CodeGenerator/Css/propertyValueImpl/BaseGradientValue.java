package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.ValueGenerator;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Project.domain.Unit.BaseUnit;

public abstract class BaseGradientValue extends ValueGenerator {

    public String generateValue(CssStyle cssStyle) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cssStyle.getName());
        stringBuilder.append("(");
        if (cssStyle.isMultipleValue()) {
            stringBuilder.append(generateBaseValue(cssStyle)) ;
        }
        stringBuilder.append(")");

        return  stringBuilder.toString();
    }

    protected String generateBaseValue(CssStyle cssStyle) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        if (cssStyle.isMultipleValue()) {
            stringBuilder.append(buildFromMultipleValue(cssStyle)) ;
        } else {
            if (cssStyle.getUnitName() != null && !cssStyle.getUnitName().isEmpty()) {
                BaseUnit firstUnit = ValueGenerator.getUnitFromNameAndValue(cssStyle.getUnitName(), cssStyle.getValue());
                stringBuilder.append(firstUnit.getValue());

            }
            if (cssStyle.getUnitNameSecond() != null && !cssStyle.getUnitNameSecond().isEmpty()) {
                BaseUnit secUnit = getUnitFromNameAndValue(cssStyle.getUnitNameSecond(), cssStyle.getValueSecond());
                stringBuilder.append(" ");
                stringBuilder.append(secUnit.getValue());
            }

            if (cssStyle.getUnitNameThird() != null && !cssStyle.getUnitNameThird().isEmpty()) {
                BaseUnit secUnit = getUnitFromNameAndValue(cssStyle.getUnitNameThird(), cssStyle.getValueThird());
                stringBuilder.append(" ");
                stringBuilder.append(secUnit.getValue());
            }

        }


        return stringBuilder.toString();

    }

    protected abstract String generateDirectionValue(CssStyle cssStyle);

    protected String buildFromMultipleValue(CssStyle cssStyle) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int length = cssStyle.getCssValues().size();
        boolean commaToAddAfterDirection = false;
        for (CssValue cssValue : cssStyle.getCssValues()) {
            if (cssValue.isSpecialValGradient()) {
                stringBuilder.append(generateDirectionValue(cssStyle));
                commaToAddAfterDirection = true;
                continue;
            }
            
            if(commaToAddAfterDirection) {
                stringBuilder.append(", ");
                commaToAddAfterDirection = false;
            }

            if (cssValue.getUnitName() != null && !cssValue.getUnitName().isEmpty()) {

                BaseUnit firstUnit = getUnitFromNameAndValue(cssValue.getUnitName(), cssValue.getValue());
                stringBuilder.append(firstUnit.getValue());
            }

            if (cssValue.getUnitNameSecond() != null && !cssValue.getUnitNameSecond().isEmpty()) {
                BaseUnit unit = getUnitFromNameAndValue(cssValue.getUnitNameSecond(), cssValue.getValueSecond());
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());
            }

            if (cssValue.getUnitNameThird() != null && !cssValue.getUnitNameThird().isEmpty()) {
                BaseUnit unit = getUnitFromNameAndValue(cssValue.getUnitNameThird(), cssValue.getValueThird());
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());
            }

            if (cssValue.getUnitNameFourth() != null && !cssValue.getUnitNameFourth().isEmpty()) {
                BaseUnit unit = getUnitFromNameAndValue(cssValue.getUnitNameFourth(), cssValue.getValueFourth());
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());
            }

            if (cssValue.getUnitNameFifth() != null && !cssValue.getUnitNameFifth().isEmpty()) {
                BaseUnit unit = getUnitFromNameAndValue(cssValue.getUnitNameFifth(), cssValue.getValueFifth());
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());
            }


            i++;

            if (i < length) {
                stringBuilder.append(", ");
            }
        }


        return stringBuilder.toString();

    }
}
