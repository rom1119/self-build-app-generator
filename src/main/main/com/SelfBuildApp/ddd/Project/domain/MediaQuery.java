package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.ValueGenerator;
import com.SelfBuildApp.ddd.Project.domain.Unit.BaseUnit;
import com.SelfBuildApp.ddd.Project.domain.Unit.UrlUnit;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.io.FileUtils;
import org.hibernate.annotations.Cascade;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "media_query" )
public class MediaQuery implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Column(name = "id", unique = true)
    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class, PropertyAccess.HtmlTagDetails.class})
    private Long id;

    @NotEmpty()
    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String name;

    @NotEmpty()
    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String color;

    @NotEmpty()
    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String colorUnitName;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "html_project_id")
    @JsonIgnore()
    private HtmlProject htmlProject;

    @Valid
    @OneToMany(mappedBy = "mediaQuery", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<CssValue> cssValues;

    @Valid
    @OneToMany(mappedBy = "mediaQuery", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore()
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<CssStyle> cssStyleList;

    @Valid
    @OneToMany(mappedBy = "mediaQuery", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIgnore()
    protected List<PseudoSelector> pseudoSelectors;

    public MediaQuery(
            Long mediaQuery_id,
            String mediaQuery_name,
            String mediaQuery_color,
            String mediaQuery_color_unit_name

    ) {
        this();
        this.id = mediaQuery_id;
        this.name = mediaQuery_name;
        this.color = mediaQuery_color;
        this.colorUnitName = mediaQuery_color_unit_name;
    }

    public MediaQuery() {
        cssValues = new ArrayList<>();
        cssStyleList = new ArrayList<>();
        pseudoSelectors = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getFullValue() throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(generateBaseValue());

        return stringBuilder.toString();
    }

    private String generateBaseValue() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        return buildFromMultipleValue();

    }

    protected String buildFromMultipleValue() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int length = cssValues.size();
        for (CssValue cssValue : cssValues) {
            if (cssValue.isInset()) {
                stringBuilder.append("inset ");

            }

            if (cssValue.getUnitNameSixth() != null && !cssValue.getUnitNameSixth().isEmpty()) {
                BaseUnit unit = getUnitFromNameAndValue(cssValue.getUnitNameSixth(), cssValue.getValueSixth());
                stringBuilder.append(unit.getValue());
                stringBuilder.append(" ");
            }

            BaseUnit firstUnit = getUnitFromNameAndValue(cssValue.getUnitName(), cssValue.getValue());
            stringBuilder.append(firstUnit.getValue());


            if (cssValue.getUnitNameSecond() != null && !cssValue.getUnitNameThird().isEmpty()) {
                BaseUnit unit = getUnitFromNameAndValue(cssValue.getUnitNameSecond(), cssValue.getValueSecond());
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());
            }

            if (cssValue.getUnitNameThird() != null && !cssValue.getUnitNameThird().isEmpty()) {
                BaseUnit unit = getUnitFromNameAndValue(cssValue.getUnitNameThird(), cssValue.getValueThird());
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());

            }

            if (cssValue.getUnitNameFourth() != null && !cssValue.getUnitNameFourth().isEmpty()) {
                BaseUnit unit = getUnitFromNameAndValue(cssValue.getUnitNameFourth(), cssValue.getValueFourth());
                stringBuilder.append(" (");
                stringBuilder.append(unit.getValue());
            }

            if (cssValue.getUnitNameFifth() != null && !cssValue.getUnitNameFifth().isEmpty()) {
                BaseUnit unit = getUnitFromNameAndValue(cssValue.getUnitNameFifth(), cssValue.getValueFifth());
                stringBuilder.append(": ");
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());
                stringBuilder.append(" )");
            }




            i++;

            if (i < length) {
                stringBuilder.append(", ");
            }
        }


        return stringBuilder.toString();

    }

    private String getUnitNameFromName(String name)
    {
        return ValueGenerator.getUnitNameFromName(name);
    }

    private BaseUnit getUnitFromNameAndValue(String name, String value) throws Exception {
        return ValueGenerator.getUnitFromNameAndValue(name, value);
    }

    public List<CssStyle> getCssStyleList() {
        return cssStyleList;
    }

    public MediaQuery addCssStyle(CssStyle cssStyle) {
        cssStyleList.add(cssStyle);
        cssStyle.setMediaQuery(this);
        return this;
    }

    public boolean hasCssStyle(CssStyle cssStyle) {

        return cssStyleList.contains(cssStyle);
    }

    public MediaQuery removeCssStyle(CssStyle cssStyle) {
        cssStyleList.remove(cssStyle);

        return this;
    }

    public void setCssStyleList(List<CssStyle> cssStyleList) {
        this.cssStyleList = cssStyleList;
    }


    public MediaQuery addCssValue(CssValue value) {
        cssValues.add(value);
        value.setMediaQuery(this);
        return this;
    }

    public MediaQuery removeCssValue(CssValue value) {
        cssValues.remove(value);

        return this;
    }

    public List<CssValue> getCssValues() {
        return cssValues;
    }

    public void setCssValues(List<CssValue> cssValues) {
        this.cssValues = cssValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HtmlProject getHtmlProject() {
        return htmlProject;
    }

    public void setHtmlProject(HtmlProject htmlProject) {
        this.htmlProject = htmlProject;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorUnitName() {
        return colorUnitName;
    }

    public void setColorUnitName(String colorUnitName) {
        this.colorUnitName = colorUnitName;
    }

    public MediaQuery addPseudoSelector(PseudoSelector pseudoSelector) {
        pseudoSelectors.add(pseudoSelector);
        pseudoSelector.setMediaQuery(this);
        return this;
    }

    public boolean hasPseudoSelector(PseudoSelector pseudoSelector) {

        return pseudoSelectors.contains(pseudoSelector);
    }

    public MediaQuery removePseudoSelector(PseudoSelector pseudoSelector) {
        pseudoSelectors.remove(pseudoSelector);

        return this;
    }

    public List<PseudoSelector> getPseudoSelectors() {
        return pseudoSelectors;
    }

    public void setPseudoSelectors(List<PseudoSelector> pseudoSelectors) {
        this.pseudoSelectors = pseudoSelectors;
    }

}
