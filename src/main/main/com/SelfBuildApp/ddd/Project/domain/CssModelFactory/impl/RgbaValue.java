package com.SelfBuildApp.ddd.Project.domain.CssModelFactory.impl;

import com.SelfBuildApp.ddd.Project.domain.CssModelFactory.CssFactory;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RgbaValue {

    private static String DELIMITER = "-";


    public static String getClearValueReplacePlaceholder( String value)
    {

        Pattern patternT = Pattern.compile("[|]{2}[ \"-:.,a-z0-9}{()]*[|]{2}");
        Matcher matcherT = patternT.matcher(value);

        while (matcherT.find()) {
            // allMatches.add(matcher.group());
            return matcherT.group().replaceAll("[|]{2}", "").replace(DELIMITER, ",");
        }

        return null;
    }

    public static String clearOriginalValueReplacePlaceholder( String value)
    {

        String v = value;

        Pattern pattern = Pattern.compile("rgba[(]{1}[ .,0-9()]*[)]{1}");
        Matcher matcher = pattern.matcher(value);

        while (matcher.find()) {
            v = v.replace(matcher.group(),
                    CssFactory.PLACEHOLDER_VAL
                            .replace("KEY", "rgba")
                            .replace("VALUE", Objects.requireNonNull(buildValue(matcher.group()))));

        }

        return v;

    }

    protected static String buildValue( String value)
    {
        Pattern pattern = Pattern.compile("rgba[(]{1}[ .,0-9()]*[)]{1}");
        Matcher matcher = pattern.matcher(value);

        List<String> allMatches = new ArrayList<String>();


        while (matcher.find()) {
            allMatches.add(matcher.group());
        }

        if (allMatches.size() > 0) {

            String rgbaStr = allMatches.get(0);
            rgbaStr =  rgbaStr.replace("rgba(", "");
            rgbaStr =  rgbaStr.replace(")", "");
            rgbaStr =  rgbaStr.trim();

            String[] rgbaArr = rgbaStr.split(",");
            String valueResult = "{";

            valueResult += "\"r\":" + rgbaArr[0].trim() + DELIMITER;
            valueResult += "\"g\":" + rgbaArr[1].trim() + DELIMITER;
            valueResult += "\"b\":" + rgbaArr[2].trim() + DELIMITER;
            valueResult += "\"a\":" + rgbaArr[3].trim() + "";

            valueResult += "}";
            return valueResult;

        }

        return null;

    }
}
