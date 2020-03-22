package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.ddd.Project.domain.Unit.BaseUnit;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGB;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGBA;
import com.SelfBuildApp.ddd.Project.domain.Unit.Named;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Pixel;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table( name = "css_style" )
@NamedNativeQuery(name = "CssStyle.findAllForProject", query = "SELECT * FROM css_style JOIN html_node ON css_style.html_tag_id=html_node.id WHERE html_node.project_id = ?;", resultClass = CssStyle.class)
public class CssStyle implements Serializable {

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

    @NotEmpty()
    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitName;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitNameSecond;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitNameThird;

    @NotEmpty()
    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String value;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueSecond;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueThird;

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

    public String getValueSecond() {
        return valueSecond;
    }

    public void setValueSecond(String valueSecond) {
        this.valueSecond = valueSecond;
    }

    public String getValueThird() {
        return valueThird;
    }

    public void setValueThird(String valueThird) {
        this.valueThird = valueThird;
    }

    public HtmlTag getHtmlTag() {
        return htmlTag;
    }

    public void setHtmlTag(HtmlTag htmlTag) {
        this.htmlTag = htmlTag;
    }

    @JsonIgnore
    public BaseUnit getUnit() {
//        unitName
        return getUnitFromNameAndValue(unitName, value);
    }

    @JsonIgnore
    public BaseUnit getUnitSecond() {
//        unitName
        return getUnitFromNameAndValue(unitNameSecond, valueSecond);
    }

    @JsonIgnore
    public BaseUnit getUnitThird() {
        return getUnitFromNameAndValue(unitNameThird, valueThird);
    }

    public void setUnitName(String unitName) {
        this.unitName =  getUnitNameFromName(unitName);
    }

    public String getUnitName() {
        return unitName;
    }

    public String getUnitNameSecond() {
        return unitNameSecond;
    }

    public void setUnitNameSecond(String unitNameSecond) {
        this.unitNameSecond = getUnitNameFromName(unitNameSecond);
    }

    public String getUnitNameThird() {
        return unitNameThird;
    }

    public void setUnitNameThird(String unitNameThird) {
        this.unitNameThird = getUnitNameFromName(unitNameThird);
    }

    public void setUnit(BaseUnit unit) {
        this.unitName = unit.getName();
    }
    public void setUnitSecond(BaseUnit unit) {
        this.unitNameSecond = unit.getName();
    }
    public void setUnitThird(BaseUnit unit) {
        this.unitNameThird = unit.getName();
    }

    private String getUnitNameFromName(String name)
    {
        switch(name) {
            case Named.NAME:
                return Named.NAME;
            case Pixel.NAME:
                return Pixel.NAME;
            case RGB.NAME:
                return RGB.NAME;
            case RGBA.NAME:
                return RGBA.NAME;
        }
        throw new NotImplementedException();
    }

    private BaseUnit getUnitFromNameAndValue(String name, String value)
    {
        switch(name) {
            case Named.NAME:
                return new Named();
            case Pixel.NAME:
                return new Pixel();
            case RGB.NAME:
                String[] params = value.split(RGB.DELIMITER, 3);
                int r = Integer.valueOf(params[0]);
                int g = Integer.valueOf(params[1]);
                int b = Integer.valueOf(params[2]);
                return new RGB(r, g, b);
            case RGBA.NAME:
                String[] paramsRgba = value.split(RGBA.DELIMITER, 4);
                int rSec = Integer.valueOf(paramsRgba[0]);
                int gSec = Integer.valueOf(paramsRgba[1]);
                int bSec = Integer.valueOf(paramsRgba[2]);
                float aSec = Float.valueOf(paramsRgba[3]);
                return new RGBA(rSec, gSec, bSec, aSec);
        }
        throw new NotImplementedException();
    }
}
