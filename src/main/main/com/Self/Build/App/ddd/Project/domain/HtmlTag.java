package com.Self.Build.App.ddd.Project.domain;

import com.Self.Build.App.ddd.Project.ProjectItem;
import com.Self.Build.App.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "html_tag" )
public class HtmlTag extends ProjectItem<HtmlProject> implements Serializable {

    @JsonView(PropertyAccess.Details.class)
    protected String tagName;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore()
    private HtmlProject project;

    @Valid
    @OneToMany(mappedBy = "htmlTag", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    private List<CssStyle> cssStyleList;

    @Valid
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    private List<HtmlTag> children;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore()
    private HtmlTag parent;

    public HtmlTag() {
        cssStyleList = new ArrayList<>();
        children = new ArrayList<>();
    }

    @Override
    public HtmlProject getProject() {
        return project;
    }

    @Override
    public ProjectItem<HtmlProject> setProject(HtmlProject project) {
        this.project =  project;

        return this;
    }

    public List<CssStyle> getCssStyleList() {
        return cssStyleList;
    }

    public HtmlTag addCssStyle(CssStyle cssStyle) {
        cssStyleList.add(cssStyle);
        cssStyle.setHtmlTag(this);
        return this;
    }

    public HtmlTag removeCssStyle(CssStyle cssStyle) {
        cssStyleList.remove(cssStyle);
        cssStyle.setHtmlTag(null);

        return this;
    }

    public void setCssStyleList(List<CssStyle> cssStyleList) {
        this.cssStyleList = cssStyleList;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public HtmlTag addChild(HtmlTag child) {
        children.add(child);
        child.setParent(this);
        return this;
    }

    public HtmlTag removeChild(HtmlTag child) {
        children.remove(child);
        child.setParent(null);

        return this;
    }

    public List<HtmlTag> getChildren() {
        return children;
    }

    public void setChildren(List<HtmlTag> children) {
        this.children = children;
    }

    public HtmlTag getParent() {
        return parent;
    }

    public void setParent(HtmlTag parent) {
        this.parent = parent;
    }
}
