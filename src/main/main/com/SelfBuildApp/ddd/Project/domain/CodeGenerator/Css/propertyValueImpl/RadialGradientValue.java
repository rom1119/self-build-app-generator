package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl;

import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Project.domain.Unit.BaseUnit;
import com.SelfBuildApp.ddd.Project.domain.Unit.Named;

public class RadialGradientValue extends BaseGradientValue {

    protected String generateDirectionValue(CssStyle cssStyle)
    {
        StringBuilder stringBuilder = new StringBuilder();

        CssValue directionVal = cssStyle.getCssValues().get(0);


        BaseUnit unit = null;
        BaseUnit firstUnit = null;
        try {
            if (directionVal.getUnitName() != null && !directionVal.getUnitName().isEmpty()) {

                firstUnit = getUnitFromNameAndValue(directionVal.getUnitName(), directionVal.getValue());
                stringBuilder.append(firstUnit.getValue());
            }

            if (directionVal.getUnitNameSecond() != null && !directionVal.getUnitNameSecond().isEmpty()) {
                unit = getUnitFromNameAndValue(directionVal.getUnitNameSecond(), directionVal.getValueSecond());
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());
            }

            if (directionVal.getUnitNameThird() != null || directionVal.getUnitNameFourth() != null) {
                stringBuilder.append(" at");
            }

            if (directionVal.getUnitNameThird() != null && !directionVal.getUnitNameThird().isEmpty()) {
                unit = getUnitFromNameAndValue(directionVal.getUnitNameThird(), directionVal.getValueThird());
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());
            }

            if (directionVal.getUnitNameFourth() != null && !directionVal.getUnitNameFourth().isEmpty()) {
                unit = getUnitFromNameAndValue(directionVal.getUnitNameFourth(), directionVal.getValueFourth());
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        return stringBuilder.toString();

    }
}
