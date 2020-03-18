package com.SelfBuildApp.ddd.Project;

import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.BaseAggregateRoot;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Project<T extends ProjectItem> extends BaseAggregateRoot {

    @Column
    @JsonView(PropertyAccess.Public.class)
    protected String name;

    public abstract Project<T>  addItem(T item);
    public abstract Project<T> removeItem(T item);
    public abstract boolean hasItem(T item);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
