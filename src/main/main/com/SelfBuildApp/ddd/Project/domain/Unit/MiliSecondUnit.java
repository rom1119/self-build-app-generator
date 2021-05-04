package com.SelfBuildApp.ddd.Project.domain.Unit;

public class MiliSecondUnit extends BaseUnit
{
    public static final String NAME = "mili-second-unit";
    protected String value;

    public MiliSecondUnit(String value) {
        this.value = value;
    }

    public MiliSecondUnit() {
    }

    @Override
    public String getValue() {
        return "" + value + "ms";
    }

    @Override
    public String getName() {
        return MiliSecondUnit.NAME;
    }
}
