package com.SelfBuildApp.ddd.Project.domain.CssModelFactory.impl;

import com.SelfBuildApp.ddd.Project.domain.CssModelFactory.CssFactory;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GradientFactory {


    public static CssStyle build(CssStyle cssMain, String value)
    {
        Pattern pattern = Pattern.compile("[a-z-]*gradient[(]{1}[a-z .,%0-9()]*[)]{1}");
        Matcher matcher = pattern.matcher(value);

        List<String> allMatches = new ArrayList<String>();


        while (matcher.find()) {
            allMatches.add(matcher.group());
        }

        for (int i = 0; i < allMatches.size(); i++) {
            CssStyle gradient = new CssStyle();
            gradient.setMultipleValue(true);

            gradient.setParent(cssMain);

            Pattern p = Pattern.compile("([a-z-]*gradient)([(]{1}[a-z .,%0-9()]*[)]{1})");
            Matcher m = p.matcher(allMatches.get(i));

            while (m.find()) {

                String name = m.group(1);
                String valueGradient = m.group(2);

                gradient.setName(name);

                initValues(gradient, valueGradient);
            }


        }

        return null;

    }

    protected static void initValues(CssStyle gradient, String valueStr)
    {
        String clearVal = RgbaValue.clearOriginalValueReplacePlaceholder(valueStr);
        clearVal = RgbValue.clearOriginalValueReplacePlaceholder(clearVal);
        String[] clearValArr = clearVal.split(",");


        for (int i = 0; i < clearValArr.length; i++) {
            String itemVal = clearValArr[i].trim();
            String[] valArr = itemVal.split(" ");

            CssValue cssValue = new CssValue();
            cssValue.setCssStyle(gradient);

            if (i == 0) {
                cssValue.setSpecialValGradient(true);
                String specialVal = valArr[0].trim();
                if (valArr[1] != null) {
                    specialVal = valArr[1].trim();
                }
                cssValue.setValue(specialVal);
                cssValue.setUnitName(CssFactory.getUnitNameFromValue(specialVal));

                continue;
            }

            String specialVal = valArr[0].trim();
            String specialValSec = valArr[1].trim();

            cssValue.setValue(CssFactory.getClearValue(specialVal));
            cssValue.setUnitName(CssFactory.getUnitNameFromValue(specialVal));

            cssValue.setValueSecond(CssFactory.getClearValue(specialValSec));
            cssValue.setUnitNameSecond(CssFactory.getUnitNameFromValue(specialValSec));


        }


    }
}
