package com.SelfBuildApp.ddd.Project.domain.Unit;

public class SecondUnit extends BaseUnit
{
    public static final String NAME = "second-unit";
    protected String value;

    public SecondUnit(String value) {
        this.value = value;
    }

    public SecondUnit() {
    }

    @Override
    public String getValue() {
        return "" + value + "s";
    }

    @Override
    public String getName() {
        return SecondUnit.NAME;
    }
}
