package com.SelfBuildApp.ddd.Project.domain.CssModelFactory;

import com.SelfBuildApp.ddd.Project.domain.CssModelFactory.impl.GradientFactory;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Project.domain.MediaQuery;

import org.springframework.stereotype.Component;

@Component
public class MediaQueryFactory {

    public MediaQuery build(MediaQuery mediaQuery, String value)
    {
        String[] clearValArr = value.split(",");
        for (int i = 0; i < clearValArr.length; i++) {
            String itemVal = clearValArr[i].trim();

            boolean inset = false;
            if (value.contains("inset")) {
                inset = true;
            }
            CssValue cssValue = new CssValue();
            cssValue.setMediaQuery(mediaQuery);

//            initValuesAndUnitsForStyleValue(cssValue, itemVal);

        }

        return mediaQuery;

    }

}
