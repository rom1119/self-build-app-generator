package com.Self.Build.App.ddd.Project.domain;

import com.Self.Build.App.ddd.Project.domain.Unit.BaseUnit;
import com.Self.Build.App.ddd.Project.domain.Unit.Named;
import com.Self.Build.App.ddd.Project.domain.Unit.Size.Pixel;
import com.Self.Build.App.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    @JsonView(PropertyAccess.Details.class)
    private String unitName;

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

    public BaseUnit getUnit() {
//        unitName
        return getUnitFromName(unitName);
    }

    public void setUnitName(String unitName) {
        BaseUnit unitFromName = getUnitFromName(unitName);
        this.unitName = unitFromName.getName();
    }

    public void setUnit(BaseUnit unit) {
        this.unitName = unit.getName();
    }

    private BaseUnit getUnitFromName(String name)
    {
        switch(unitName) {
            case Named.NAME:
                return new Named(value);
            case Pixel.NAME:
                return new Pixel(value);
        }
        throw new NotImplementedException();
    }
}
