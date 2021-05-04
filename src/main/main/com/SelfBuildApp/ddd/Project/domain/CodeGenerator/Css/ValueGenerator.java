package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css;

import com.SelfBuildApp.ddd.Project.domain.Unit.*;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGB;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGBA;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.EM;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Percent;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Pixel;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.REM;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public abstract class ValueGenerator {

    public static BaseUnit getUnitFromNameAndValue(String name, String value) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map map;
        switch(name) {
            case Named.NAME:
                return new Named(value);
            case Pixel.NAME:
                return new Pixel(value);
            case Percent.NAME:
                return new Percent(value);
            case EM.NAME:
                return new EM(value);
            case REM.NAME:
                return new REM(value);
            case UrlUnit.NAME:
                return new UrlUnit(value);
            case SecondUnit.NAME:
                return new SecondUnit(value);
            case MiliSecondUnit.NAME:
                return new MiliSecondUnit(value);
            case DegUnit.NAME:
                return new DegUnit(value);
            case TurnUnit.NAME:
                return new TurnUnit(value);
            case RGB.NAME:
//                String[] params = value.split(RGB.DELIMITER, 3);
                // convert JSON string to Map
                map = mapper.readValue(value, Map.class);
                int r = (int) map.get("r");
                int g = (int) map.get("g");
                int b = (int) map.get("b");
                return new RGB(r, g, b);
            case RGBA.NAME:
                String[] paramsRgba = value.split(RGBA.DELIMITER, 4);
                map = mapper.readValue(value, Map.class);

                double aSec = 1.0;
                if (map.get("a") instanceof Integer) {
                    aSec = ((int) map.get("a"));
                }  else if (map.get("a") instanceof Double) {
                    aSec = ((double) map.get("a"));
                }
                int rSec = (int) map.get("r");
                int gSec = (int) map.get("g");
                int bSec = (int) map.get("b");
                return new RGBA(rSec, gSec, bSec, aSec);
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
    }

    public static String getUnitNameFromName(String name)
    {

        if (name == null) {
            return "";
        }
        switch(name) {
            case Named.NAME:
                return Named.NAME;
            case Percent.NAME:
                return Percent.NAME;
            case EM.NAME:
                return EM.NAME;
            case REM.NAME:
                return REM.NAME;
            case Pixel.NAME:
                return Pixel.NAME;
            case RGB.NAME:
                return RGB.NAME;
            case RGBA.NAME:
                return RGBA.NAME;
            case UrlUnit.NAME:
                return UrlUnit.NAME;
            case SecondUnit.NAME:
                return SecondUnit.NAME;
            case MiliSecondUnit.NAME:
                return MiliSecondUnit.NAME;
            case DegUnit.NAME:
                return DegUnit.NAME;
            case TurnUnit.NAME:
                return TurnUnit.NAME;
        }
            return null;
    }
}
