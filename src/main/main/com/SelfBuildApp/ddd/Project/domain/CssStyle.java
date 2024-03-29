package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.Storage.FileInterface;
import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.ValueGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl.BaseGradientValue;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl.FontFamilyValue;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl.LinearGradientValue;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.propertyValueImpl.RadialGradientValue;
import com.SelfBuildApp.ddd.Project.domain.CssModelFactory.CssFactory;
import com.SelfBuildApp.ddd.Project.domain.Unit.*;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGB;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGBA;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.EM;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Percent;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Pixel;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.REM;
import com.SelfBuildApp.ddd.Project.infrastructure.repo.HtmlAttrConverter;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.infrastructure.Validation.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table( name = "css_style" )
public class CssStyle implements Serializable, FileInterface {


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

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitNameFourth;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String unitNameFifth;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String value;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueSecond;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueThird;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueFourth;

    @JsonView({PropertyAccess.List.class, PropertyAccess.Details.class})
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueFifth;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String resourceUrl;

    @JsonIgnore()
    private String resourceFilename;

    @JsonIgnore()
    private String resourceFileExtension;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    private boolean multipleValue;

    @Valid
    @OneToMany(mappedBy = "cssStyle", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView({PropertyAccess.HtmlTagDetails.class, PropertyAccess.Details.class})
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<CssValue> cssValues;

    @Valid
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView({PropertyAccess.HtmlTagDetails.class, PropertyAccess.Details.class})
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<CssStyle> children;

    @ManyToOne( fetch = FetchType.LAZY, targetEntity = CssStyle.class)
    @JoinColumn(name = "parent_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CssStyle parent;

    @Transient
    @JsonIgnore
    private PathFileManager pathFileManager;

    @Transient
    @JsonIgnore
    private List<Long> issetEntitiesIdsValues = new ArrayList<>();


    @JsonIgnore
    private String cssIdentity;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "html_tag_id")
    @JsonIgnore()
    private HtmlTag htmlTag;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "pseudo_selector_id")
    @JsonIgnore()
    private PseudoSelector pseudoSelector;

    @ManyToOne()
    @JoinColumn(name = "media_query_id")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView({PropertyAccess.HtmlTagDetails.class, PropertyAccess.Details.class})
    private MediaQuery mediaQuery;

    @Transient
    public String htmlTagId;

    @Transient
    public Long mediaQueryId;

    @Transient
    public Long parentId;

    @Transient
    public Long pseudoSelectorId;

    public CssStyle(
            Long cssS_id,
            String cssS_css_identity,
            String cssS_multiple_value,
            String cssS_name,
            String cssS_unit_name,
            String cssS_unit_name_second,
            String cssS_unit_name_third,
            String cssS_unit_name_fourth,
            String cssS_unit_name_fifth,

            String cssS_value,
            String cssS_value_second,
            String cssS_value_third,
            String cssS_value_fourth,
            String cssS_value_fifth,

            String cssS_html_tag_id,
            Long cssS_media_query_id,
            Long cssS_parent_id,
            Long cssS_pseudo_selector_id,

            String cssS_resource_filename,
            String cssS_resource_file_extension,
            String cssS_resource_url
    ) {
        this();
        id = cssS_id;
        cssIdentity = cssS_css_identity;
        multipleValue = cssS_multiple_value != null ? Integer.valueOf(cssS_multiple_value) == 1 : false;
        name = cssS_name;

        unitName = cssS_unit_name;
        unitNameSecond = cssS_unit_name_second;
        unitNameThird = cssS_unit_name_third;
        unitNameFourth = cssS_unit_name_fourth;
        unitNameFifth = cssS_unit_name_fifth;

        htmlTagId = cssS_html_tag_id;
        mediaQueryId = cssS_media_query_id;
        parentId = cssS_parent_id;
        pseudoSelectorId = cssS_pseudo_selector_id;

        value = cssS_value;
        valueSecond = cssS_value_second;
        valueThird = cssS_value_third;
        valueFourth = cssS_value_fourth;
        valueFifth = cssS_value_fifth;

        resourceFilename = cssS_resource_filename;
        resourceFileExtension = cssS_resource_file_extension;
        resourceUrl = cssS_resource_url;

    }

    public CssStyle() {
        cssValues = new ArrayList<>();
        children = new ArrayList<>();
    }

    public CssStyle(@NotEmpty() String name, @NotEmpty() String value) {
        this();
        this.name = name;
        this.value = value;
    }

    public String getHtmlTagId() {
        return htmlTagId;
    }

    public void setHtmlTagId(String htmlTagId) {
        this.htmlTagId = htmlTagId;
    }

    public Long getMediaQueryId() {
        return mediaQueryId;
    }

    public void setMediaQueryId(Long mediaQueryId) {
        this.mediaQueryId = mediaQueryId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getPseudoSelectorId() {
        return pseudoSelectorId;
    }

    public void setPseudoSelectorId(Long pseudoSelectorId) {
        this.pseudoSelectorId = pseudoSelectorId;
    }

    @JsonIgnore
    public PathFileManager getPathFileManager() {
        if (pathFileManager == null) {
            if (htmlTag != null) {
                return htmlTag.getPathFileManager();

            } else if(parent != null){
                return parent.getPathFileManager();
            } else {
                return pseudoSelector.getPathFileManager();

            }
        }
        return pathFileManager;
    }

    public void setPathFileManager(PathFileManager pathFileManager) {
        this.pathFileManager = pathFileManager;
    }

    public List<Long> getIssetEntitiesIdsValues() {
        return issetEntitiesIdsValues;
    }

    public void setIssetEntitiesIdsValues(List<Long> issetEntitiesIdsValues) {
        this.issetEntitiesIdsValues = issetEntitiesIdsValues;
    }

    public void setCssIdentity(String cssIdentity) {
        this.cssIdentity = cssIdentity;
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

        CssStyle cssStyle = (CssStyle) o;

        if (!getName().equals(cssStyle.getName())) return false;
        if (!getUnitName().equals(cssStyle.getUnitName())) return false;
        if (getUnitNameSecond() != null ? !getUnitNameSecond().equals(cssStyle.getUnitNameSecond()) : cssStyle.getUnitNameSecond() != null)
            return false;
        if (getUnitNameThird() != null ? !getUnitNameThird().equals(cssStyle.getUnitNameThird()) : cssStyle.getUnitNameThird() != null)
            return false;
        if (getValue() != null ? !getValue().equals(cssStyle.getValue()) : cssStyle.getValue() != null) return false;
        if (getValueSecond() != null ? !getValueSecond().equals(cssStyle.getValueSecond()) : cssStyle.getValueSecond() != null)
            return false;

        if (getValueFourth() != null ? !getValueFourth().equals(cssStyle.getValueFourth()) : cssStyle.getValueFourth() != null)
            return false;

        if (getValueFifth() != null ? !getValueFifth().equals(cssStyle.getValueFifth()) : cssStyle.getValueFifth() != null)
            return false;
        return getValueThird() != null ? getValueThird().equals(cssStyle.getValueThird()) : cssStyle.getValueThird() == null;
    }

    @Override
    public int hashCode() {
        int result = getName().trim().hashCode();
        result = 3 * result + getUnitName().trim().hashCode();
        result = 2 * result + (getUnitNameSecond() != null ? getUnitNameSecond().trim().hashCode() : 0);
        result = 4 * result + (getUnitNameThird() != null ? getUnitNameThird().trim().hashCode() : 0);
        result = 6 * result + (getValue() != null ? getValue().trim().hashCode() : 0);
        result = 5 * result + (getValueSecond() != null ? getValueSecond().trim().hashCode() : 0);
        result = 7 * result + (getValueThird() != null ? getValueThird().trim().hashCode() : 0);
        result = 7 * result + (getValueFourth() != null ? getValueFourth().trim().hashCode() : 0);
        result = 7 * result + (getValueFifth() != null ? getValueFifth().trim().hashCode() : 0);
        result = 8 * result + (getResourceUrl() != null ? getResourceUrl().trim().hashCode() : 0);
        result = 9 * result + (getResourceFilename() != null ? getResourceFilename().trim().hashCode() : 0);
        int childrenHashCode = 0;
        for (CssStyle css : children) {
            childrenHashCode += css.hashCode();
        }
        result = result + childrenHashCode;

        int valuesHashCode = 0;
        for (CssValue cssValue : cssValues) {
            valuesHashCode += cssValue.hashCode();
        }
        result = result + valuesHashCode;
        return result;
    }
    @PreUpdate
    public void preUpdate() {
        this.updateCssIdentity();
        if (parent != null) {
            parent.updateCssIdentity();
        }
    }


    @PrePersist
    public void prePersist() {
        this.updateCssIdentity();
        if (parent != null) {
            parent.updateCssIdentity();
        }
    }

    public void updateCssIdentity()
    {
        cssIdentity = Integer.toHexString(hashCode());
    }

    @PostRemove
    public void postRemove() {
        deleteResource();
    }

    public PseudoSelector getPseudoSelector() {
        return pseudoSelector;
    }

    public void setPseudoSelector(PseudoSelector pseudoSelector) {
        this.pseudoSelector = pseudoSelector;
    }

    public String getCssIdentity() {
        return cssIdentity;
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

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getResourcePath() throws Exception {
        if (getResourceFilename() ==  null) {
            return null;
        }
        if (getResourceFilename().isEmpty()) {
            return null;
        }
//        System.out.println(RESOURCE_DIR());
//        System.out.println(UPLOAD_DIR());
        return RESOURCE_DIR() + getResourceFilename() + "." + getResourceFileExtension();
    }

    public String getResourceFilename() {
        return resourceFilename;
    }

    public void setResourceFilename(String resourceFilename) {
        this.resourceFilename = resourceFilename;
    }

    @JsonIgnore
    public String getResourceFileExtension() {
        return resourceFileExtension;
    }

    public void setResourceFileExtension(String resourceFileExtension) {
        this.resourceFileExtension = resourceFileExtension;
    }

    public boolean isFontFamily()
    {
        if (name == null) {
            return false;
        }
        return name.contains("font-family");
    }

    public boolean isGradient()
    {
        if (name == null) {
            return false;
        }
        return name.contains("gradient");
    }

    public boolean isLinearGradient()
    {
        if (name == null) {
            return false;
        }
        return name.contains("linear-gradient");
    }

    public boolean isRadialGradient()
    {
        if (name == null) {
            return false;
        }
        return name.contains("radial-gradient");
    }

    private String generateValueForGradient() throws Exception {
        BaseGradientValue gradientValue;
        if (isLinearGradient()) {
            gradientValue = new LinearGradientValue();
        } else if (isRadialGradient()) {
            gradientValue = new RadialGradientValue();

        } else {
            throw new Exception("Gradient value with name " + getName() + " is not implemented.");
        }

        return  gradientValue.generateValue(this);
    }

    @JsonIgnore
    public String getFullValue() throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        if (children.size() > 0) {

            for (CssStyle child : children) {
                if (child.isGradient()) {
                    stringBuilder.append(child.generateValueForGradient());

                } else {
                    throw new Exception("Property with name " + getName() + " is not implemented to generate value as child");
                }
            }
        } else  {

                stringBuilder.append(generateBaseValue());
        }


        return stringBuilder.toString();
    }

    private String generateBaseValue() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        if (isFontFamily())  {
            FontFamilyValue fontFamilyValue = new FontFamilyValue();
            stringBuilder.append(fontFamilyValue.generateValue(this));

            return stringBuilder.toString();
        }
        if (isMultipleValue()) {
            stringBuilder.append(buildFromMultipleValue()) ;
            return stringBuilder.toString();

        }

        if (getUnitName() != null && !getUnitName().isEmpty()) {
            BaseUnit firstUnit;
            if (getUnit() instanceof UrlUnit) {
                if (getResourcePath() != null) {
                    firstUnit = getUnitFromNameAndValue(getUnitName(), getResourcePath());
                } else {
                    firstUnit = getUnitFromNameAndValue(getUnitName(), getResourceUrl());

                }
            } else {
                firstUnit = getUnitFromNameAndValue(getUnitName(), getValue());

            }
            stringBuilder.append(firstUnit.getValue());

        }
        if (getUnitNameSecond() != null && !getUnitNameSecond().isEmpty()) {
            BaseUnit secUnit = getUnitFromNameAndValue(getUnitNameSecond(), getValueSecond());
            stringBuilder.append(" ");
            stringBuilder.append(secUnit.getValue());
        }

        if (getUnitNameThird() != null && !getUnitNameThird().isEmpty()) {
            BaseUnit secUnit = getUnitFromNameAndValue(getUnitNameThird(), getValueThird());
            stringBuilder.append(" ");
            stringBuilder.append(secUnit.getValue());
        }

        if (getUnitNameFourth() != null && !getUnitNameFourth().isEmpty()) {
            BaseUnit secUnit = getUnitFromNameAndValue(getUnitNameFourth(), getValueFourth());
            stringBuilder.append(" ");
            stringBuilder.append(secUnit.getValue());
        }

        if (getUnitNameFifth() != null && !getUnitNameFifth().isEmpty()) {
            BaseUnit secUnit = getUnitFromNameAndValue(getUnitNameFifth(), getValueFifth());
            stringBuilder.append(" ");
            stringBuilder.append(secUnit.getValue());
        }

        return stringBuilder.toString();

    }

    protected String buildFromMultipleValue() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int length = cssValues.size();
        for (CssValue cssValue : cssValues) {
            if (name.equals("transform")) {
                stringBuilder.append(cssValue.getValueNinth());
                stringBuilder.append("(");
                stringBuilder.append(cssValue.generateBaseValue());
                stringBuilder.append(")");

            } else {
                stringBuilder.append(cssValue.generateBaseValue());

            }

            i++;

            if (i < length) {
                stringBuilder.append(", ");
            }
        }


        return stringBuilder.toString();

    }



    private String getUnitNameFromValue(String value)
    {
        return CssFactory.getUnitNameFromValue(value);
    }

    private String getUnitNameFromName(String name)
    {
        return ValueGenerator.getUnitNameFromName(name);
    }

    private BaseUnit getUnitFromNameAndValue(String name, String value) throws Exception {
        return ValueGenerator.getUnitFromNameAndValue(name, value);
    }

    public void deleteResource()
    {
        if (resourceFilename == null) {
            return;
        }
        if (resourceFilename.isEmpty()) {
            return;
        }

        String DIR = null;
        try {
            DIR = UPLOAD_DIR();
            DIR = DIR.substring(0, DIR.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(DIR);
        File targetFile = new File(DIR);
        try {
            FileUtils.deleteDirectory(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setResourceFileExtension(null);
        setResourceFilename(null);
    }

    public void saveResource(MultipartFile file) {
        String filename = getCssIdentity();
        String extension = file.getOriginalFilename().split("[.]")[1];

        String DIR = null;
        try {
            DIR = UPLOAD_DIR();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File directory = new File(DIR);
        if (! directory.exists()){
            directory.mkdirs();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

        File targetFile = new File(DIR + filename  + "."  + extension);
        try {
            file.transferTo(targetFile);
            setResourceFileExtension(extension);
            setResourceFilename(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @JsonIgnore
    public String getFileName() {
        return resourceFilename + "." + resourceFileExtension;
    }

    @Override
    @JsonIgnore
    public String getDirName() {
        StringBuilder dir = new StringBuilder();
        dir.append("project/");
        dir.append(htmlTag.getProjectId());
        dir.append("/css_style/");
        dir.append(getId());
        dir.append("/");

        return dir.toString();
    }

    public boolean isMultipleValue() {
        return multipleValue;
    }

    public void setMultipleValue(boolean multipleValue) {
        this.multipleValue = multipleValue;
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

    public CssStyle addCssValue(CssValue value) {
        cssValues.add(value);
        value.setCssStyle(this);
        return this;
    }

    public CssStyle removeCssValue(CssValue value) {
        cssValues.remove(value);

        return this;
    }

    public List<CssValue> getCssValues() {
        return cssValues;
    }

    public void setCssValues(List<CssValue> cssValues) {
        this.cssValues = cssValues;
    }

    public List<CssStyle> getChildren() {
        return children;
    }

    public void setChildren(List<CssStyle> children) {
        this.children = children;
    }

    public CssStyle addChild(CssStyle value) {
        children.add(value);
        value.setParent(this);
        return this;
    }

    public CssStyle removeChild(CssStyle value) {
        children.remove(value);

        return this;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public CssStyle getParent() {
        return parent;
    }

    public void setParent(CssStyle parent) {
        this.parent = parent;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public MediaQuery getMediaQuery() {
        return mediaQuery;
    }

    public void setMediaQuery(MediaQuery mediaQuery) {
        this.mediaQuery = mediaQuery;
    }

    public String UPLOAD_DIR() throws Exception {
        StringBuilder dir = new StringBuilder();
        if (getPathFileManager() == null) {
            throw new Exception("pathFileManager is null");
        }
        dir.append(getPathFileManager().getBaseUploadDir());
        dir.append("project/");
        if (htmlTag != null) {
            dir.append(htmlTag.getProjectId());

        } else {
            dir.append(pseudoSelector.getOwner().getProjectId());

        }
        dir.append("/css_style/");
        dir.append(getId());
        dir.append("/");

        return dir.toString();
    }

    public String RESOURCE_DIR() throws Exception {
        StringBuilder dir = new StringBuilder();
        if (getPathFileManager() == null) {
            throw new Exception("pathFileManager is null");
        }
        dir.append(getPathFileManager().getResourceUploadDir());
        dir.append("project/");
        if (htmlTag != null) {
            dir.append(htmlTag.getProjectId());

        } else {
            dir.append(pseudoSelector.getOwner().getProjectId());

        }
        dir.append("/css_style/");
        dir.append(getId());
        dir.append("/");

        return dir.toString();
    }
}
