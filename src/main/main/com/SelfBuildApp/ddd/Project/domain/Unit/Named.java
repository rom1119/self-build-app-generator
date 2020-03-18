package com.SelfBuildApp.ddd.Project.domain.Unit;

public class Named extends ColorUnit
{
    public static final String NAME = "named-unit";
    protected String value;

    public Named(String value) {
        this.value = value;
    }

    public Named() {
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getName() {
        return Named.NAME;
    }
}
