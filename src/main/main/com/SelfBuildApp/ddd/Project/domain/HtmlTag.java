package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.ddd.Project.ProjectItem;
import com.SelfBuildApp.ddd.Project.infrastructure.repo.HtmlAttrConverter;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@NamedNativeQuery(name = "HtmlTag.findMainHtmlTagsForProject", query = "SELECT * FROM html_node WHERE parent_id is null and project_id = ?;", resultClass = HtmlNode.class)
public class HtmlTag extends HtmlNode {

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    protected String tagName;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Convert(converter = HtmlAttrConverter.class)
    protected Map<String, HtmlTagAttr> attrs;

    @Valid
    @OneToMany(mappedBy = "htmlTag", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<CssStyle> cssStyleList;

    @Valid
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<PseudoSelector> pseudoSelectors;

    @Valid
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OrderBy("orderNumber")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    protected List<HtmlNode> children;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    private boolean closingTag = true;

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
}
