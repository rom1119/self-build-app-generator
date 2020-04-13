package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.ddd.Project.ProjectItem;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "html_node" )
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class HtmlNode extends ProjectItem<HtmlProject> implements Serializable {

//    @JsonView(PropertyAccess.Details.class)
//    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
//    protected String nodeType;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Column(name = "order_number")
    protected int orderNumber = 1;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected HtmlProject project;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore()
    protected HtmlNode parent;

    @Override
    public HtmlProject getProject() {
        return project;
    }

    @Override
    public ProjectItem<HtmlProject> setProject(HtmlProject project) {
        this.project =  project;

        return this;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public HtmlNode getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public void setParent(HtmlNode parent) {
        this.parent = parent;
    }

    public String getProjectId() {
        return project.getId();
    }
}
