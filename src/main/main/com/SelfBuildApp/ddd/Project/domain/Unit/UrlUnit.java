package com.SelfBuildApp.ddd.Project.domain.Unit;

public class UrlUnit extends BaseUnit
{
    public static final String NAME = "url-unit";
    protected String value;

    public UrlUnit(String value) {
        this.value = value;
    }

    public UrlUnit() {
    }

    @Override
    public String getValue() {
        return "url(" + value + ")";
    }

    @Override
    public String getName() {
        return UrlUnit.NAME;
    }
}
