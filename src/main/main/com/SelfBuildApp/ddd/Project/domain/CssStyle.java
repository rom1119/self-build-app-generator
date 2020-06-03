package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.Storage.FileInterface;
import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Project.domain.Unit.BaseUnit;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGB;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGBA;
import com.SelfBuildApp.ddd.Project.domain.Unit.Named;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.EM;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Percent;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Pixel;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.REM;
import com.SelfBuildApp.ddd.Project.domain.Unit.UrlUnit;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.infrastructure.Validation.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String value;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueSecond;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String valueThird;

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
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<CssValue> cssValues;

    @Transient
    @JsonIgnore
    private PathFileManager pathFileManager;

    @JsonIgnore
    private String cssIdentity;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "html_tag_id")
    @JsonIgnore()
    private HtmlTag htmlTag;

    public CssStyle() {
        cssValues = new ArrayList<>();

    }

    public CssStyle(@NotEmpty() String name, @NotEmpty() String value) {
        this();
        this.name = name;
        this.value = value;
    }

    public PathFileManager getPathFileManager() {
        if (pathFileManager == null) {
            return htmlTag.getPathFileManager();
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

        CssStyle cssStyle = (CssStyle) o;

        if (!getName().equals(cssStyle.getName())) return false;
        if (!getUnitName().equals(cssStyle.getUnitName())) return false;
        if (getUnitNameSecond() != null ? !getUnitNameSecond().equals(cssStyle.getUnitNameSecond()) : cssStyle.getUnitNameSecond() != null)
            return false;
        if (getUnitNameThird() != null ? !getUnitNameThird().equals(cssStyle.getUnitNameThird()) : cssStyle.getUnitNameThird() != null)
            return false;
        if (!getValue().equals(cssStyle.getValue())) return false;
        if (getValueSecond() != null ? !getValueSecond().equals(cssStyle.getValueSecond()) : cssStyle.getValueSecond() != null)
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
        return result;
    }
    @PreUpdate
    public void preUpdate() {
        cssIdentity = Integer.toHexString(hashCode());
    }


    @PrePersist
    public void prePersist() {
        cssIdentity = Integer.toHexString(hashCode());
    }

    @PostRemove
    public void postRemove() {
        deleteResource();
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

    @JsonIgnore
    public String getFullValue() throws Exception {

        if (isMultipleValue()) {
            return buildFromMultipleValue();
        }
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

    protected String buildFromMultipleValue() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int length = cssValues.size();
        for (CssValue cssValue : cssValues) {
            if (cssValue.isInset()) {
                stringBuilder.append("inset ");

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
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());
            }

            if (cssValue.getUnitNameFifth() != null && !cssValue.getUnitNameFifth().isEmpty()) {
                BaseUnit unit = getUnitFromNameAndValue(cssValue.getUnitNameFifth(), cssValue.getValueFifth());
                stringBuilder.append(" ");
                stringBuilder.append(unit.getValue());
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

        if (name == null) {
            return "";
        }
        switch(name) {
            case Named.NAME:
                return Named.NAME;
            case Percent.NAME:
                return Percent.NAME;
            case EM.NAME:
                return EM.NAME;
            case REM.NAME:
                return REM.NAME;
            case Pixel.NAME:
                return Pixel.NAME;
            case RGB.NAME:
                return RGB.NAME;
            case RGBA.NAME:
                return RGBA.NAME;
            case UrlUnit.NAME:
                return UrlUnit.NAME;
        }
        throw new NotImplementedException();
    }

    private BaseUnit getUnitFromNameAndValue(String name, String value) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map map;
        switch(name) {
            case Named.NAME:
                return new Named(value);
            case Pixel.NAME:
                return new Pixel(value);
            case Percent.NAME:
                return new Percent(value);
            case EM.NAME:
                return new EM(value);
            case REM.NAME:
                return new REM(value);
            case UrlUnit.NAME:
                return new UrlUnit(value);
            case RGB.NAME:
//                String[] params = value.split(RGB.DELIMITER, 3);
                // convert JSON string to Map
                map = mapper.readValue(value, Map.class);
                int r = (int) map.get("r");
                int g = (int) map.get("g");
                int b = (int) map.get("b");
                return new RGB(r, g, b);
            case RGBA.NAME:
                String[] paramsRgba = value.split(RGBA.DELIMITER, 4);
                map = mapper.readValue(value, Map.class);

                double aSec = 1.0;
                if (map.get("a") instanceof Integer) {
                    aSec = ((int) map.get("a"));
                }  else if (map.get("a") instanceof Double) {
                    aSec = ((double) map.get("a"));
                }
                int rSec = (int) map.get("r");
                int gSec = (int) map.get("g");
                int bSec = (int) map.get("b");
                return new RGBA(rSec, gSec, bSec, aSec);
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
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

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String UPLOAD_DIR() throws Exception {
        StringBuilder dir = new StringBuilder();
        if (getPathFileManager() == null) {
            throw new Exception("pathFileManager is null");
        }
        dir.append(getPathFileManager().getBaseUploadDir());
        dir.append("project/");
        dir.append(htmlTag.getProjectId());
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
        dir.append(htmlTag.getProjectId());
        dir.append("/css_style/");
        dir.append(getId());
        dir.append("/");

        return dir.toString();
    }
}
