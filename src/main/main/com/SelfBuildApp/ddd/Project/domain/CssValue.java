package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.Storage.FileInterface;
import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.ValueGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl.BaseGradientValue;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl.FontFamilyValue;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl.LinearGradientValue;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl.RadialGradientValue;
import com.SelfBuildApp.ddd.Project.domain.Unit.*;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGB;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGBA;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.EM;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Percent;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Pixel;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.REM;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.Map;

@Entity
@Table( name = "css_value" )
public class CssValue implements Serializable {



    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Column(name = "id", unique = true)
    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    private Long id;

    @NotEmpty()
    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitName;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitNameSecond;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitNameThird;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitNameFourth;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitNameFifth;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitNameSixth;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitNameSeventh;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitNameEighth;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String value;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueSecond;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueThird;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueFourth;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueFifth;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueSixth;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueSeventh;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueEighth;

//    @JsonIgnore()
//    private String resourceFilename;
//
//    @JsonIgnore()
//    private String resourceFileExtension;

    @Transient
    @JsonIgnore
    private PathFileManager pathFileManager;

    @JsonIgnore
    private String cssIdentity;

    @ManyToOne( fetch = FetchType.LAZY, targetEntity = CssStyle.class)
    @JoinColumn(name = "css_style_id")
    @JsonIgnore()
    private CssStyle cssStyle;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "media_query_id")
    @JsonIgnore()
    private MediaQuery mediaQuery;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    private boolean inset = false;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    private boolean specialValGradient = false;


    public CssValue() {
    }

    public CssValue(@NotEmpty() String value, @NotEmpty() String unitName) {
        this.value = value;
        this.unitName = unitName;
    }

    public PathFileManager getPathFileManager() {
        if (pathFileManager == null) {
            return cssStyle.getPathFileManager();
        }
        return pathFileManager;
    }

    public void setPathFileManager(PathFileManager pathFileManager) {
        this.pathFileManager = pathFileManager;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CssValue cssValue = (CssValue) o;

        if (!getUnitName().equals(cssValue.getUnitName())) return false;
        if (getUnitNameSecond() != null ? !getUnitNameSecond().equals(cssValue.getUnitNameSecond()) : cssValue.getUnitNameSecond() != null)
            return false;
        if (getUnitNameThird() != null ? !getUnitNameThird().equals(cssValue.getUnitNameThird()) : cssValue.getUnitNameThird() != null)
            return false;

        if (getValue() != null ? !getValue().equals(cssValue.getValue()) : cssValue.getValue() != null)
            return false;
        if (getValueSecond() != null ? !getValueSecond().equals(cssValue.getValueSecond()) : cssValue.getValueSecond() != null)
            return false;
        return getValueThird() != null ? getValueThird().equals(cssValue.getValueThird()) : cssValue.getValueThird() == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 3 * result + getUnitName().trim().hashCode();
        result = 2 * result + (getUnitNameSecond() != null ? getUnitNameSecond().trim().hashCode() : 0);
        result = 4 * result + (getUnitNameThird() != null ? getUnitNameThird().trim().hashCode() : 0);
        result = 11 * result + (getUnitNameFourth() != null ? getUnitNameFourth().trim().hashCode() : 0);
        result = 8 * result + (getUnitNameFifth() != null ? getUnitNameFifth().trim().hashCode() : 0);
        result = 6 * result + (getValue() != null ? getValue().trim().hashCode() : 0);
        result = 5 * result + (getValueSecond() != null ? getValueSecond().trim().hashCode() : 0);
        result = 7 * result + (getValueThird() != null ? getValueThird().trim().hashCode() : 0);
        result = 9 * result + (getValueFourth() != null ? getValueFourth().trim().hashCode() : 0);
        result = 10 * result + (getValueFifth() != null ? getValueFifth().trim().hashCode() : 0);
        result = 15 * result + (getValueSixth() != null ? getValueSixth().trim().hashCode() : 0);
        result = 18 * result + (getValueSeventh() != null ? getValueSeventh().trim().hashCode() : 0);
        result = 19 * result + (getValueEighth() != null ? getValueEighth().trim().hashCode() : 0);
        return result;
    }
    @PreUpdate
    public void preUpdate() {
        cssIdentity = Integer.toHexString(hashCode());
        if (cssStyle != null) {
            cssStyle.updateCssIdentity();
        }
    }


    @PrePersist
    public void prePersist() {
        cssIdentity = Integer.toHexString(hashCode());

        if (cssStyle != null) {
            cssStyle.updateCssIdentity();
        }
    }




    public String getCssIdentity() {
        return cssIdentity;
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

    @JsonIgnore
    public BaseUnit getUnit() throws Exception {
//        unitName
        return getUnitFromNameAndValue(unitName, value);
    }

    @JsonIgnore
    public BaseUnit getUnitSecond() throws Exception {
//        unitName
        return getUnitFromNameAndValue(unitNameSecond, valueSecond);
    }

    @JsonIgnore
    public BaseUnit getUnitThird() throws Exception {
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

    public String getUnitNameFourth() {
        return unitNameFourth;
    }

    public void setUnitNameFourth(String unitNameFourth) {
        this.unitNameFourth = unitNameFourth;
    }

    public String getUnitNameFifth() {
        return unitNameFifth;
    }

    public void setUnitNameFifth(String unitNameFifth) {
        this.unitNameFifth = unitNameFifth;
    }

    public String getValueFourth() {
        return valueFourth;
    }

    public void setValueFourth(String valueFourth) {
        this.valueFourth = valueFourth;
    }

    public String getValueFifth() {
        return valueFifth;
    }

    public void setValueFifth(String valueFifth) {
        this.valueFifth = valueFifth;
    }

    public CssStyle getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(CssStyle cssStyle) {
        this.cssStyle = cssStyle;
    }

    public boolean isInset() {
        return inset;
    }

    public void setInset(boolean inset) {
        this.inset = inset;
    }

    public boolean isSpecialValGradient() {
        return specialValGradient;
    }

    public void setSpecialValGradient(boolean specialValGradient) {
        this.specialValGradient = specialValGradient;
    }

    public MediaQuery getMediaQuery() {
        return mediaQuery;
    }

    public void setMediaQuery(MediaQuery mediaQuery) {
        this.mediaQuery = mediaQuery;
    }

    //    public String getResourceFilename() {
//        return resourceFilename;
//    }
//
//    public void setResourceFilename(String resourceFilename) {
//        this.resourceFilename = resourceFilename;
//    }
//
//    @JsonIgnore
//    public String getResourceFileExtension() {
//        return resourceFileExtension;
//    }
//
//    public void setResourceFileExtension(String resourceFileExtension) {
//        this.resourceFileExtension = resourceFileExtension;
//    }

//    @JsonIgnore
//    public String generateValueForFontFamily() throws Exception {
//
//        FontFamilyValue gradientValue = new FontFamilyValue();
//        if (isLinearGradient()) {
//            gradientValue = new LinearGradientValue();
//
//
//        return  gradientValue.generateValue(this);
//    }

    @JsonIgnore
    public String generateBaseValue() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        if (isInset()) {
            stringBuilder.append("inset ");

        }
        BaseUnit firstUnit = getUnitFromNameAndValue(getUnitName(), getValue());
        stringBuilder.append(firstUnit.getValue());

        if (getUnitNameSecond() != null && !getUnitNameThird().isEmpty()) {
            BaseUnit unit = getUnitFromNameAndValue(getUnitNameSecond(), getValueSecond());
            stringBuilder.append(" ");
            stringBuilder.append(unit.getValue());
        }

        if (getUnitNameThird() != null && !getUnitNameThird().isEmpty()) {
            BaseUnit unit = getUnitFromNameAndValue(getUnitNameThird(), getValueThird());
            stringBuilder.append(" ");
            stringBuilder.append(unit.getValue());
        }

        if (getUnitNameFourth() != null && !getUnitNameFourth().isEmpty()) {
            BaseUnit unit = getUnitFromNameAndValue(getUnitNameFourth(), getValueFourth());
            stringBuilder.append(" ");
            stringBuilder.append(unit.getValue());
        }

        if (getUnitNameFifth() != null && !getUnitNameFifth().isEmpty()) {
            BaseUnit unit = getUnitFromNameAndValue(getUnitNameFifth(), getValueFifth());
            stringBuilder.append(" ");
            stringBuilder.append(unit.getValue());
        }

        return stringBuilder.toString();

    }

    @JsonIgnore
    public String getFullValue() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        BaseUnit firstUnit = getUnitFromNameAndValue(getUnitName(), getValue());
        stringBuilder.append(firstUnit.getValue());

        if (getUnitNameSecond() != null && !getUnitNameThird().isEmpty()) {
            BaseUnit secUnit = getUnitFromNameAndValue(getUnitNameSecond(), getValueSecond());
            stringBuilder.append(" ");
            stringBuilder.append(secUnit.getValue());
        }

        if (getUnitNameThird() != null && !getUnitNameThird().isEmpty()) {
            BaseUnit secUnit = getUnitFromNameAndValue(getUnitNameThird(), getValueThird());
            stringBuilder.append(" ");
            stringBuilder.append(secUnit.getValue());
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

    public String getUnitNameSixth() {
        return unitNameSixth;
    }

    public void setUnitNameSixth(String unitNameSixth) {
        this.unitNameSixth = unitNameSixth;
    }

    public String getValueSixth() {
        return valueSixth;
    }

    public void setValueSixth(String valueSixth) {
        this.valueSixth = valueSixth;
    }

    public String getUnitNameSeventh() {
        return unitNameSeventh;
    }

    public void setUnitNameSeventh(String unitNameSeventh) {
        this.unitNameSeventh = unitNameSeventh;
    }

    public String getUnitNameEighth() {
        return unitNameEighth;
    }

    public void setUnitNameEighth(String unitNameEighth) {
        this.unitNameEighth = unitNameEighth;
    }

    public String getValueSeventh() {
        return valueSeventh;
    }

    public void setValueSeventh(String valueSeventh) {
        this.valueSeventh = valueSeventh;
    }

    public String getValueEighth() {
        return valueEighth;
    }

    public void setValueEighth(String valueEighth) {
        this.valueEighth = valueEighth;
    }
}
