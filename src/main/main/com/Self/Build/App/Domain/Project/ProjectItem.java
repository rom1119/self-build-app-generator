package com.Self.Build.App.Domain.Project;

import com.Self.Build.App.Domain.BaseAggregateRoot;
import com.Self.Build.App.User.Model.Privilege;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

public abstract class ProjectItem<T extends Project> extends BaseAggregateRoot {

    protected String name;
    protected String value;

    public abstract T getProject();
    public abstract ProjectItem<T> setProject(T project);
}
