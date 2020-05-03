package com.SelfBuildApp.ddd.Project.domain.Unit.Size;

import com.SelfBuildApp.ddd.Project.domain.Unit.SizeUnit;

public class EM extends SizeUnit
{
    public static final String NAME = "em-size-unit";
    protected String value;

    public EM(String value) {
        this.value = value;
    }

    public EM() {
    }

    @Override
    public String getValue() {
        return value + "em";
    }

    @Override
    public String getName() {
        return EM.NAME;
    }
}
