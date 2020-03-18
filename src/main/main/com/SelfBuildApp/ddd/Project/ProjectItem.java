package com.SelfBuildApp.ddd.Project;

import com.SelfBuildApp.ddd.Support.infrastructure.repository.BaseAggregateRoot;

public abstract class ProjectItem<T extends Project> extends BaseAggregateRoot {

    public abstract T getProject();
    public abstract ProjectItem<T> setProject(T project);
}
