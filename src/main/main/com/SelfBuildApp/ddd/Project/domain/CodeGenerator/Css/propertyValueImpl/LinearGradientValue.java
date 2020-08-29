package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl;

import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Project.domain.Unit.Named;

public class LinearGradientValue extends BaseGradientValue {

    protected String generateDirectionValue(CssStyle cssStyle)
    {
        StringBuilder stringBuilder = new StringBuilder();

        CssValue directionVal = cssStyle.getCssValues().get(0);

        try {
            if (directionVal.getUnit() instanceof Named) {
                stringBuilder.append("to ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        stringBuilder.append(directionVal.getValue());

        return stringBuilder.toString();

    }
}
