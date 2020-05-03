package com.SelfBuildApp.ddd.Project.domain.Unit.Size;

import com.SelfBuildApp.ddd.Project.domain.Unit.SizeUnit;

public class Percent extends SizeUnit
{
    public static final String NAME = "percent-size-unit";
    protected String value;

    public Percent(String value) {
        this.value = value;
    }

    public Percent() {
    }

    @Override
    public String getValue() {
        return value + "%";
    }

    @Override
    public String getName() {
        return Percent.NAME;
    }
}
