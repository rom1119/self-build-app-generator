package com.SelfBuildApp.ddd.Project.domain.CssModelFactory;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.ValueGenerator;
import com.SelfBuildApp.ddd.Project.domain.CssModelFactory.impl.GradientFactory;
import com.SelfBuildApp.ddd.Project.domain.CssModelFactory.impl.RgbValue;
import com.SelfBuildApp.ddd.Project.domain.CssModelFactory.impl.RgbaValue;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Project.domain.Unit.*;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGB;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGBA;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.EM;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Percent;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Pixel;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.REM;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CssFactory {

    public static String PLACEHOLDER_VAL = "{{{__KEY__:||VALUE||}}}";
    public CssStyle build(String name,String value)
    {
        List<String> excludedCss = Arrays.asList("windows", "orphans",
                "break-inside", "appearance", "touch-action", "pointer-events", "user-select");

        if (excludedCss.contains(name)) {
            return null;
        }
        System.out.println(name);

        CssStyle css = new CssStyle();
        css.setName(name);
        if (name.contains("background-image")) {

            if (value.contains("gradient")) {
                return GradientFactory.build(css, value);
            }
        }

        if (name.contains("background-image") ||
                name.equals("transform") ||
                name.contains("shadow") ||
                name.equals("transition")) {
            css.setMultipleValue(true);

            if (value.contains("rgba")) {
                value = RgbaValue.clearOriginalValueReplacePlaceholder(value);
            } else if (value.contains("rgb")) {
                value = RgbValue.clearOriginalValueReplacePlaceholder(value);
            }

            String[] clearValArr = value.split(",");
            for (int i = 0; i < clearValArr.length; i++) {
                String itemVal = clearValArr[i].trim();

                boolean inset = false;
                if (value.contains("inset")) {
                    inset = true;
                }
                CssValue cssValue = new CssValue();
                cssValue.setCssStyle(css);
                cssValue.setInset(inset);

                initValuesAndUnitsForStyleValue(cssValue, itemVal);
            }



        }

        initValuesAndUnitsForStyle(css, value);


        return css;

    }

    public void initValuesAndUnitsForStyle(CssStyle css, String fullValue)
    {
        String val = fullValue.trim();
        System.out.println(val);

        if (val.contains("rgba")) {
            val = RgbaValue.clearOriginalValueReplacePlaceholder(val);
        } else if (val.contains("rgb")) {
            val = RgbValue.clearOriginalValueReplacePlaceholder(val);
        } else if (val.contains("calc")) {
            String v = val;
            String unitName = getUnitNameFromValue(v);
            String valueOne = v;
            css.setValue(valueOne);
            css.setUnitName(unitName);

            return;
        }

        List<String> valArr = new ArrayList<>(Arrays.asList(val.split(" ")));

        if (valArr.size() > 0) {
            String v = valArr.get(0).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            css.setValue(valueOne);
            css.setUnitName(unitName);
        }

        if (valArr.size() > 1) {
            String v = valArr.get(1).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            css.setValueSecond(valueOne);
            css.setUnitNameSecond(unitName);
        }

        if (valArr.size() > 2) {
            String v = valArr.get(2).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            css.setValueThird(valueOne);
            css.setUnitNameThird(unitName);
        }

        if (valArr.size() > 3) {
            String v = valArr.get(3).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            css.setValueFourth(valueOne);
            css.setUnitNameFourth(unitName);
        }

        if (valArr.size() > 4) {
            String v = valArr.get(4).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            css.setValueFifth(valueOne);
            css.setUnitNameFifth(unitName);
        }

    }

    public void initValuesAndUnitsForStyleValue(CssValue cssValue, String fullValue)
    {
        String val = fullValue.trim();
        System.out.println(val);
        if (val.contains("rgba")) {
            val = RgbaValue.clearOriginalValueReplacePlaceholder(val);
        } else if (val.contains("rgb")) {
            val = RgbValue.clearOriginalValueReplacePlaceholder(val);
        } else if (val.contains("calc")) {
            String v = val;
            String unitName = getUnitNameFromValue(v);
            String valueOne = v;
            cssValue.setValue(valueOne);
            cssValue.setUnitName(unitName);

            return;
        }

        List<String> valArr = new ArrayList<>(Arrays.asList(val.split(" ")));

        if (valArr.size() > 0) {
            String v = valArr.get(0).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            cssValue.setValue(valueOne);
            cssValue.setUnitName(unitName);
        }

        if (valArr.size() > 1) {
            String v = valArr.get(1).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            cssValue.setValueSecond(valueOne);
            cssValue.setUnitNameSecond(unitName);
        }

        if (valArr.size() > 2) {
            String v = valArr.get(2).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            cssValue.setValueThird(valueOne);
            cssValue.setUnitNameThird(unitName);
        }

        if (valArr.size() > 3) {
            String v = valArr.get(3).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            cssValue.setValueFourth(valueOne);
            cssValue.setUnitNameFourth(unitName);
        }

        if (valArr.size() > 4) {
            String v = valArr.get(4).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            cssValue.setValueFifth(valueOne);
            cssValue.setUnitNameFifth(unitName);
        }

        if (valArr.size() > 5) {
            String v = valArr.get(5).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            cssValue.setValueSixth(valueOne);
            cssValue.setUnitNameSixth(unitName);
        }

        if (valArr.size() > 6) {
            String v = valArr.get(6).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            cssValue.setValueSeventh(valueOne);
            cssValue.setUnitNameSeventh(unitName);
        }

        if (valArr.size() > 7) {
            String v = valArr.get(7).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            cssValue.setValueEighth(valueOne);
            cssValue.setUnitNameEighth(unitName);
        }

        if (valArr.size() > 8) {
            String v = valArr.get(8).trim();
            String unitName = getUnitNameFromValue(v);
            String valueOne = getClearValue(v);
            cssValue.setValueNinth(valueOne);
            cssValue.setUnitNameNinth(unitName);
        }

    }


    public static String getClearValue(String value)
    {
        if (value == null) {
            return "";
        }
        System.out.println(value);
        String newValue = value;

        Pattern pattern = Pattern.compile("[0-9,.]+s");
        Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            newValue = newValue.replace("s", "").replace(",", "");
            return newValue;
        }

        if (value.contains("rgba")) {
            newValue =  RgbaValue.getClearValueReplacePlaceholder(newValue);
        } else if (value.contains("rgb")) {
            newValue =  RgbValue.getClearValueReplacePlaceholder(newValue);
        } else if (value.contains("rem")) {
            newValue =  newValue.replace("rem", "");
        } else if (value.contains("em")) {
            newValue =  newValue.replace("em", "");

        } else if (value.contains("%")) {
            newValue =  newValue.replace("%", "");

        } else if (value.contains("px")) {
            newValue =  newValue.replace("px", "");

        } else if (value.contains("url(")) {
            newValue =  newValue.replace("url(", "");
            newValue =  newValue.replace(")", "");

        } else if (value.contains("ms")) {
            newValue =  newValue.replace("ms", "");

        } else if (value.contains("deg")) {
            newValue =  newValue.replace("deg", "");

        } else if (value.contains("turn")) {
            newValue =  newValue.replace("turn", "");

        }
        return newValue;
    }

    public static String getUnitNameFromValue(String value)
    {
        if (value == null) {
            return "";
        }

        Pattern pattern = Pattern.compile("[0-9,.]+s");
        Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            return SecondUnit.NAME;
        }

        if (value.contains("rgba")) {
            return RGBA.NAME;

        } else if (value.contains("rgb")) {
            return RGB.NAME;
        } else if (value.contains("rem")) {
            return REM.NAME;
        } else if (value.contains("em")) {
            return EM.NAME;

        } else if (value.contains("%")) {
            return Percent.NAME;

        } else if (value.contains("px")) {
            return Pixel.NAME;

        } else if (value.contains("url(")) {
            return UrlUnit.NAME;

        } else if (value.contains("ms")) {
            return MiliSecondUnit.NAME;

        } else if (value.contains("deg")) {
            return DegUnit.NAME;

        } else if (value.contains("turn")) {
            return TurnUnit.NAME;

        } else {
            return Named.NAME;
        }

    }
}
