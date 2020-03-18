package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.ddd.Project.ProjectItem;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HtmlTag extends HtmlNode {

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    protected String tagName;

    @Valid
    @OneToMany(mappedBy = "htmlTag", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<CssStyle> cssStyleList;

    @Valid
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OrderBy("orderNumber")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    protected List<HtmlNode> children;

    public HtmlTag() {
        cssStyleList = new ArrayList<>();
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
        cssStyle.setHtmlTag(null);

        return this;
    }

    public void setCssStyleList(List<CssStyle> cssStyleList) {
        this.cssStyleList = cssStyleList;
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
}
