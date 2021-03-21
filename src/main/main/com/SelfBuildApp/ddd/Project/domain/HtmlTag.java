package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.ddd.Project.ProjectItem;
import com.SelfBuildApp.ddd.Project.infrastructure.repo.HtmlAttrConverter;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.io.FileUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@NamedNativeQuery(name = "HtmlTag.findMainHtmlTagsForProject", query = "SELECT * FROM html_node WHERE parent_id is null and project_id = ?;", resultClass = HtmlNode.class)
public class HtmlTag extends HtmlNode {

    @JsonView(PropertyAccess.HtmlTagDetails.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    protected String tagName;

    @JsonView(PropertyAccess.HtmlTagDetails.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Convert(converter = HtmlAttrConverter.class)
    protected Map<String, HtmlTagAttr> attrs;

    @Valid
    @OneToMany(mappedBy = "htmlTag", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<CssStyle> cssStyleList;

    @Valid
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<PseudoSelector> pseudoSelectors;

    @Valid
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("orderNumber")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<HtmlNode> children;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    private boolean closingTag = true;

    @JsonIgnore()
    private String resourceFilename;

    @JsonIgnore()
    private String resourceFileExtension;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    private String resourceUrl;

    public HtmlTag() {
        cssStyleList = new ArrayList<>();
        pseudoSelectors = new ArrayList<>();
        children = new ArrayList<>();
    }

    public List<CssStyle> getCssStyleList() {
        return cssStyleList;
    }

    public HtmlTag addCssStyle(CssStyle cssStyle) {
        cssStyleList.add(cssStyle);
        cssStyle.setHtmlTag(this);
        return this;
    }

    public boolean hasCssStyle(CssStyle cssStyle) {

        return cssStyleList.contains(cssStyle);
    }

    public HtmlTag removeCssStyle(CssStyle cssStyle) {
        cssStyleList.remove(cssStyle);

        return this;
    }

    public void setCssStyleList(List<CssStyle> cssStyleList) {
        this.cssStyleList = cssStyleList;
    }

    public HtmlTag addPseudoSelector(PseudoSelector pseudoSelector) {
        pseudoSelectors.add(pseudoSelector);
        pseudoSelector.setOwner(this);
        return this;
    }

    public boolean hasPseudoSelector(PseudoSelector pseudoSelector) {

        return pseudoSelectors.contains(pseudoSelector);
    }

    public HtmlTag removePseudoSelector(PseudoSelector pseudoSelector) {
        pseudoSelectors.remove(pseudoSelector);

        return this;
    }

    public List<PseudoSelector> getPseudoSelectors() {
        return pseudoSelectors;
    }

    public void setPseudoSelectors(List<PseudoSelector> pseudoSelectors) {
        this.pseudoSelectors = pseudoSelectors;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public HtmlTag appendChild(HtmlNode item) {
        int orderNumber = children.size() + 1;
        addChild(item);
        item.setOrderNumber(orderNumber);
        return this;
    }

    public void updateOrderChildren()
    {
        int i = 0;
        for (HtmlNode item:
             children) {
            i++;
            item.setOrderNumber(i);
        }
    }


    public HtmlTag addChild(HtmlNode child) {
        children.add(child);
        child.setParent(this);
        return this;
    }

    public HtmlTag removeChild(HtmlNode child) {
        children.remove(child);
        child.setParent(null);
        recalculateOrders(child.orderNumber);

        return this;
    }

    private void recalculateOrders(int startOrder)
    {
        for(HtmlNode tag : children) {
            if (tag.orderNumber > startOrder) {
                tag.orderNumber--;
            }
        }
    }

    public String UPLOAD_DIR() throws Exception {
        StringBuilder dir = new StringBuilder();
        if (getPathFileManager() == null) {
            throw new Exception("pathFileManager is null");
        }
        dir.append(getPathFileManager().getBaseUploadDir());
        dir.append("project/");
        dir.append(getProjectId());

        dir.append("/html_tag/");
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
        dir.append(getProjectId());

        dir.append("/html_tag/");
        dir.append(getId());
        dir.append("/");

        return dir.toString();
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
        String filename = Integer.toHexString(hashCode());;
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

    public List<HtmlNode> getChildren() {
        return children;
    }

    public void setChildren(List<HtmlNode> children) {
        this.children = children;
    }


    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }


    public Map<String, HtmlTagAttr> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, HtmlTagAttr> attrs) {
        this.attrs = attrs;
    }

    public boolean isClosingTag() {
        return closingTag;
    }

    public void setClosingTag(boolean closingTag) {
        this.closingTag = closingTag;
    }

    public String getResourceFilename() {
        return resourceFilename;
    }

    public void setResourceFilename(String resourceFilename) {
        this.resourceFilename = resourceFilename;
    }

    public String getResourceFileExtension() {
        return resourceFileExtension;
    }

    public void setResourceFileExtension(String resourceFileExtension) {
        this.resourceFileExtension = resourceFileExtension;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}
