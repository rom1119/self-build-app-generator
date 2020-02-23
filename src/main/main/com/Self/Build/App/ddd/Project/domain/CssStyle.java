package com.Self.Build.App.ddd.Project.domain;

import com.Self.Build.App.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table( name = "css_style" )
public class CssStyle implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "id", unique = true)
    @JsonView(PropertyAccess.Details.class)
    private Long id;

    @NotEmpty()
    @JsonView(PropertyAccess.Details.class)
    private String name;

    @NotEmpty()
    @JsonView(PropertyAccess.Details.class)
    private String value;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "html_tag_id")
    @JsonIgnore()
    private HtmlTag htmlTag;

    public CssStyle() {
    }

    public CssStyle(@NotEmpty() String name, @NotEmpty() String value) {
        this.name = name;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HtmlTag getHtmlTag() {
        return htmlTag;
    }

    public void setHtmlTag(HtmlTag htmlTag) {
        this.htmlTag = htmlTag;
    }
}
