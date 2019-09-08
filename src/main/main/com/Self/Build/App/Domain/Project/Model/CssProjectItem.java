package com.Self.Build.App.Domain.Project.Model;

import com.Self.Build.App.Domain.Project.Project;
import com.Self.Build.App.Domain.Project.ProjectItem;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "css_project_item" )
public class CssProjectItem extends ProjectItem<CssProject> {

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private CssProject project;

    @Override
    public CssProject getProject() {
        return project;
    }

    @Override
    public ProjectItem<CssProject> setProject(CssProject project) {
        this.project =  project;

        return this;
    }
}
