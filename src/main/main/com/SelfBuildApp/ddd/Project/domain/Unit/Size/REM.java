package com.SelfBuildApp.ddd.Project.domain.Unit.Size;

import com.SelfBuildApp.ddd.Project.domain.Unit.SizeUnit;

public class REM extends SizeUnit
{
    public static final String NAME = "rem-size-unit";
    protected String value;

    public REM(String value) {
        this.value = value;
    }

    public REM() {
    }

    @Override
    public String getValue() {
        return value + "rem";
    }

    @Override
    public String getName() {
        return REM.NAME;
    }
}
