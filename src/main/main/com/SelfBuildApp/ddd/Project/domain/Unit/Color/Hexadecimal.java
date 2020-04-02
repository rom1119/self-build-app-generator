package com.SelfBuildApp.ddd.Project.domain.Unit.Color;

import com.SelfBuildApp.ddd.Project.domain.Unit.ColorUnit;

public class Hexadecimal extends ColorUnit
{
    public static final String NAME = "hexadecimal-color-unit";

    protected int val;

    public Hexadecimal(int val) {
        this.val = val;
    }

    @Override
    public String getValue() {
        return "#" + val;
    }

    @Override
    public String getName() {
        return Hexadecimal.NAME;
    }
}
