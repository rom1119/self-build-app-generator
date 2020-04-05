package com.SelfBuildApp.ddd.Project.domain.Unit.Color;

import com.SelfBuildApp.ddd.Project.domain.Unit.ColorUnit;

public class RGBA extends ColorUnit
{
    public static final String NAME = "rgba-color-unit";
    public static final String DELIMITER = ",";

    protected int r;
    protected int g;
    protected int b;
    protected double a;

    public RGBA(int r, int g, int b, double a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public String getValue() {
        return "rgba(" + r + ", " + g + ", " + b + ", " + a + ")";
    }

    @Override
    public String getName() {
        return RGBA.NAME;
    }
}
