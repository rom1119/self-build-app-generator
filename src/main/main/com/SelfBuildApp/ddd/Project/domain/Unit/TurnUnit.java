package com.SelfBuildApp.ddd.Project.domain.Unit;

public class TurnUnit extends BaseUnit
{
    public static final String NAME = "turn-unit";
    protected String value;

    public TurnUnit(String value) {
        this.value = value;
    }

    public TurnUnit() {
    }

    @Override
    public String getValue() {
        return "" + value + "turn";
    }

    @Override
    public String getName() {
        return TurnUnit.NAME;
    }
}
