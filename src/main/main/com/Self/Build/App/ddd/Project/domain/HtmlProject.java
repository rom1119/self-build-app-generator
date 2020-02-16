package com.Self.Build.App.ddd.Project.domain;

import com.Self.Build.App.ddd.Project.Project;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "html_project" )
@JsonDeserialize()
public class HtmlProject extends Project<HtmlTag> implements Serializable {

    @OneToMany(mappedBy = "project")
    @Fetch(FetchMode.SELECT)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Set<HtmlTag> items;

    public HtmlProject() {
        items = new HashSet<>();
    }

    @Override
    public Project<HtmlTag> addItem(HtmlTag item) {
        items.add(item);
        item.setProject(this);
        return this;
    }

    @Override
    public Project<HtmlTag> removeItem(HtmlTag item) {
        items.remove(item);
        item.setProject(null);
        return this;
    }

    @Override
    public boolean hasItem(HtmlTag item) {
        return items.contains(item);
    }
}
