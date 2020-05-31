package com.SelfBuildApp.ddd.Project.domain.Unit.Color;

import com.SelfBuildApp.ddd.Project.domain.Unit.ColorUnit;

public class RGB extends ColorUnit
{
    public static final String NAME = "rgb-color-unit";
    public static final String DELIMITER = ",";

    protected int r;
    protected int g;
    protected int b;

    public RGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public String getValue() {
        return "rgb(" + r + ", " + g + ", " + b + ")";
    }

    @Override
    public String getName() {
        return RGB.NAME;
    }
}
