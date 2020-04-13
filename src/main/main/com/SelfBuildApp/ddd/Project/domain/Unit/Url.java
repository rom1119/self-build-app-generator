package com.SelfBuildApp.ddd.Project.domain.Unit;

public class Url extends BaseUnit
{
    public static final String NAME = "url-unit";
    protected String value;

    public Url(String value) {
        this.value = value;
    }

    public Url() {
    }

    @Override
    public String getValue() {
        return "url(" + value + ")";
    }

    @Override
    public String getName() {
        return Url.NAME;
    }
}
