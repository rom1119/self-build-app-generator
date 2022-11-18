package com.SelfBuildApp.ddd.Project.domain.CssModelFactory.impl;

import com.SelfBuildApp.ddd.Project.domain.CssModelFactory.CssFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RgbValue {

    public static String buildValue( String value)
    {
        Pattern pattern = Pattern.compile("rgb[(]{1}[ .,0-9()]*[)]{1}");
        Matcher matcher = pattern.matcher(value);

        List<String> allMatches = new ArrayList<String>();


        while (matcher.find()) {
            allMatches.add(matcher.group());
        }

        if (allMatches.size() > 0) {

            String rgbaStr = allMatches.get(0);
            rgbaStr =  rgbaStr.replace("rgb(", "");
            rgbaStr =  rgbaStr.replace(")", "");
            rgbaStr =  rgbaStr.trim();

            String[] rgbaArr = rgbaStr.split(",");
            String valueResult = "{";

            valueResult += "\"r\":" + rgbaArr[0].trim() + ",";
            valueResult += "\"g\":" + rgbaArr[1].trim() + ",";
            valueResult += "\"b\":" + rgbaArr[2].trim() + "";

            valueResult += "}";
            return valueResult;

        }

        return null;

    }

    public static List<String> fetchColorValues(String value)
    {

        Pattern pattern = Pattern.compile("rgb[(]{1}[ .,0-9()]*[)]{1}");
        Matcher matcher = pattern.matcher(value);

        List<String> allMatches = new ArrayList<String>();


        while (matcher.find()) {
            allMatches.add(matcher.group());
        }

        return allMatches;
    }

    public static String clearOriginalValue( String value)
    {

        String v = value;
        v = v.replaceAll("rgb[(]{1}[ .,0-9()]*[)]{1}", "");
        v = v.replace("(", "");
        v = v.replace(")", "");

        return v;

    }

    public static String getClearValueReplacePlaceholder( String value)
    {

        Pattern patternT = Pattern.compile("[|]{2}[ \":.,a-z0-9}{()]*[|]{2}");
        Matcher matcherT = patternT.matcher(value);

        while (matcherT.find()) {
            return matcherT.group().replaceAll("[|]{2}", "");
        }

        return null;
    }

    public static String clearOriginalValueReplacePlaceholder( String value)
    {

        String v = value;

        Pattern pattern = Pattern.compile("rgb[(]{1}[ .,0-9()]*[)]{1}");
        Matcher matcher = pattern.matcher(value);

        while (matcher.find()) {
            v = v.replace(matcher.group(),
                    CssFactory.PLACEHOLDER_VAL.replace("KEY", "rgb").replace("VALUE", Objects.requireNonNull(buildValue(matcher.group()))));

        }

        return v;

    }
}
