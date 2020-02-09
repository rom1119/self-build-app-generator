package com.Self.Build.App.ddd.Project.domain;

import com.Self.Build.App.ddd.Project.Project;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "html_project" )
public class HtmlProject extends Project<HtmlTag> {

    @OneToMany(mappedBy = "project")
    @Fetch(FetchMode.SELECT)
    protected Set<HtmlTag> items;

    public HtmlProject() {
        items = new HashSet<>();
    }

    @Override
    public Project<HtmlTag> addItem(HtmlTag item) {
        items.add(item);
        return this;
    }

    @Override
    public Project<HtmlTag> removeItem(HtmlTag item) {
        items.remove(item);
        return this;
    }

    @Override
    public boolean hasItem(HtmlTag item) {
        return items.contains(item);
    }
}
