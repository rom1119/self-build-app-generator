package com.Self.Build.App.ddd.Project.domain;

import com.Self.Build.App.ddd.Project.Project;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "css_project" )
public class CssProject extends Project<CssProjectItem> {

    @OneToMany(mappedBy = "project")
    @Fetch(FetchMode.SELECT)
    protected Set<CssProjectItem> items;

    public CssProject() {
        items = new HashSet<>();
    }

    @Override
    public Project<CssProjectItem> addItem(CssProjectItem item) {
        items.add(item);
        return this;
    }

    @Override
    public Project<CssProjectItem> removeItem(CssProjectItem item) {
        items.remove(item);
        return this;
    }

    @Override
    public boolean hasItem(CssProjectItem item) {
        return items.contains(item);
    }
}
