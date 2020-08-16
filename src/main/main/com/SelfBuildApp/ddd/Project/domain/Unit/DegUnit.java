package com.SelfBuildApp.ddd.Project.domain.Unit;

public class DegUnit extends BaseUnit
{
    public static final String NAME = "deg-unit";
    protected String value;

    public DegUnit(String value) {
        this.value = value;
    }

    public DegUnit() {
    }

    @Override
    public String getValue() {
        return "" + value + "deg";
    }

    @Override
    public String getName() {
        return DegUnit.NAME;
    }
}
