package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.ddd.Project.domain.Unit.BaseUnit;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGB;
import com.SelfBuildApp.ddd.Project.domain.Unit.Color.RGBA;
import com.SelfBuildApp.ddd.Project.domain.Unit.Named;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Pixel;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.infrastructure.Validation.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.*;
import java.util.Map;

@Entity
@Table( name = "css_style" )
public class CssStyle implements Serializable {

    public String UPLOAD_DIR()
    {
        StringBuilder dir = new StringBuilder();
        dir.append("./uploads/project/");
        dir.append(htmlTag.getProjectId());
        dir.append("/css_style/");
        dir.append(getId());
        dir.append("/");

        return dir.toString();
    }

    @Image
    private MultipartFile file;

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

    @JsonIgnore()
    private String resourceFilename;

    @JsonIgnore()
    private String resourceFileExtension;

    private String cssIdentity;

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
        result = 6 * result + getValue().trim().hashCode();
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
//        cssIdentity = Integer.toHexString(hashCode());

        if (getResourceFilename() != null) {
            File file = new File(getResourcePath());

            file.delete();

        }

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

    public String getResourcePath()
    {
        if (getResourceFilename() ==  null) {
            return null;
        }

        return UPLOAD_DIR() + "/" + getResourceFilename() + "." + getResourceFileExtension();
    }

    @JsonIgnore
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

        if (name == null) {
            return "";
        }
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

    private BaseUnit getUnitFromNameAndValue(String name, String value) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map map;
        switch(name) {
            case Named.NAME:
                return new Named(value);
            case Pixel.NAME:
                return new Pixel(value);
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

    public void saveResource(InputStream inputStream, String filename, String extension) {
//        new File(inputStream.)

        byte[] buffer = new byte[0];
        try {
            buffer = new byte[inputStream.available()];
        } catch (IOException e) {
            e.printStackTrace();
        }
        File targetFile = new File(UPLOAD_DIR() + filename  + "."  + extension);
        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
