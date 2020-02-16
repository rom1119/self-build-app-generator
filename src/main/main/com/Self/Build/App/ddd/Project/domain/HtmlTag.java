package com.Self.Build.App.ddd.Project.domain;

import com.Self.Build.App.ddd.Project.ProjectItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "html_tag" )
public class HtmlTag extends ProjectItem<HtmlProject> implements Serializable {

    protected String tagName;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore()
    private HtmlProject project;

    @Valid
    @OneToMany(mappedBy = "htmlTag", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private List<CssStyle> cssStyleList;

    public HtmlTag() {
        cssStyleList = new ArrayList<>();
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
}
