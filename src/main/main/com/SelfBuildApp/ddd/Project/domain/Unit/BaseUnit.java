package com.SelfBuildApp.ddd.Project.domain.Unit;

public abstract class BaseUnit {

    private String name;

    abstract public String getValue();
    abstract public String getName();

    public BaseUnit() {
    }
}
