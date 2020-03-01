package com.Self.Build.App.ddd.Project.domain.Unit;

import com.Self.Build.App.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

public abstract class BaseUnit {

    private String name;

    abstract public String getValue();
    abstract public String getName();

    public BaseUnit() {
    }
}
