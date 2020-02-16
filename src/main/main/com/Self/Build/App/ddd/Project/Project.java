package com.Self.Build.App.ddd.Project;

import com.Self.Build.App.ddd.Support.infrastructure.repository.BaseAggregateRoot;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Project<T extends ProjectItem> extends BaseAggregateRoot {

    @Column
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
