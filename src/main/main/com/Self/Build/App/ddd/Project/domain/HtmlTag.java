package com.Self.Build.App.ddd.Project.domain;

import com.Self.Build.App.ddd.Project.ProjectItem;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "html_tag" )
public class HtmlTag extends ProjectItem<HtmlProject> {

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private HtmlProject project;

    @Override
    public HtmlProject getProject() {
        return project;
    }

    @Override
    public ProjectItem<HtmlProject> setProject(HtmlProject project) {
        this.project =  project;

        return this;
    }
}
