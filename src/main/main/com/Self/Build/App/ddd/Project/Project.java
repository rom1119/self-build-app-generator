package com.Self.Build.App.ddd.Project;

import com.Self.Build.App.ddd.Support.infrastructure.repository.BaseAggregateRoot;

public abstract class Project<T extends ProjectItem> extends BaseAggregateRoot {

    private String name;

    public abstract Project<T>  addItem(T item);
    public abstract Project<T> removeItem(T item);
    public abstract boolean hasItem(T item);
}
