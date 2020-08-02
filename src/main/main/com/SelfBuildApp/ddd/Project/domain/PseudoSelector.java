package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "pseudo_selector" )
public class PseudoSelector implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Column(name = "id", unique = true)
    @JsonView(PropertyAccess.Details.class)
    private Long id;

    @NotEmpty()
    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String name;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String delimiter;

//    @NotEmpty()
//    @JsonView(PropertyAccess.Details.class)
//    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
//    private String targetSelector;

    @Valid
    @OneToMany(mappedBy = "pseudoSelector", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<CssStyle> cssStyleList;


    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "html_tag_id")
    @JsonIgnore()
    private HtmlTag owner;

    @Transient
    @JsonIgnore
    private PathFileManager pathFileManager;


    public void setPathFileManager(PathFileManager pathFileManager) {
        this.pathFileManager = pathFileManager;
    }

    public PathFileManager getPathFileManager() {
        if (pathFileManager == null) {
            return owner.getPathFileManager();
        }
        return pathFileManager;
    }

    public PseudoSelector() {
        cssStyleList = new ArrayList<>();
    }

    public PseudoSelector(@NotEmpty() String name) {
        this();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HtmlTag getOwner() {
        return owner;
    }

    public void setOwner(HtmlTag owner) {
        this.owner = owner;
    }

    public List<CssStyle> getCssStyleList() {
        return cssStyleList;
    }

    public PseudoSelector addCssStyle(CssStyle cssStyle) {
        cssStyleList.add(cssStyle);
        cssStyle.setPseudoSelector(this);
        return this;
    }

    public boolean hasCssStyle(CssStyle cssStyle) {

        return cssStyleList.contains(cssStyle);
    }

    public PseudoSelector removeCssStyle(CssStyle cssStyle) {
        cssStyleList.remove(cssStyle);

        return this;
    }

    public void setCssStyleList(List<CssStyle> cssStyleList) {
        this.cssStyleList = cssStyleList;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
