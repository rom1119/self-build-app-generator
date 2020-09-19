package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Project.Project;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table( name = "html_project" )
@JsonDeserialize()
public class HtmlProject extends Project<HtmlNode> implements Serializable {

    @OneToMany(mappedBy = "project")
    @Fetch(FetchMode.SELECT)
    @OrderBy("orderNumber")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Set<HtmlNode> items;

    @Valid
    @OneToMany(mappedBy = "htmlProject", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<MediaQuery> mediaQueryList;


    @Valid
    @OneToMany(mappedBy = "htmlProject", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<KeyFrame> keyFrameList;

    @Transient
    @JsonIgnore
    private PathFileManager pathFileManager;

    public HtmlProject() {
        items = new HashSet<>();
        mediaQueryList = new ArrayList<>();
        keyFrameList = new ArrayList<>();
    }

    public void setPathFileManager(PathFileManager pathFileManager) {
        this.pathFileManager = pathFileManager;
    }

    public PathFileManager getPathFileManager() {
        return pathFileManager;
    }

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty("items")
    public Set<HtmlNode> getMainItems()
    {
        Set<HtmlNode> list = new HashSet<>();
        this.getItems().size();
        Set<HtmlNode> asd = this.items;

        for (HtmlNode item: asd) {
            if (!item.hasParent()) {
                list.add(item);
            }
        }

        return list;
    }

    public Project<HtmlNode> appendChild(HtmlNode item) {
        int firsttLvlCount = 1;
        for (HtmlNode itemEl: items) {
            if (!itemEl.hasParent()) {
                firsttLvlCount++;
            }
        }
//        int orderNumber =
        addItem(item);
        item.setOrderNumber(firsttLvlCount);
        return this;
    }

    public Set<HtmlNode> getItems() {
        return items;
    }

    public Project<HtmlNode> addItem(HtmlNode item) {
        items.add(item);
        item.setProject(this);
        return this;
    }

    @Override
    public Project<HtmlNode> removeItem(HtmlNode item) {
        items.remove(item);
        item.setProject(null);
        recalculateOrders(item.getOrderNumber());

        return this;
    }

    private void recalculateOrders(int startOrder)
    {
        for(HtmlNode tag : items) {
            if (tag.hasParent()) {
                continue;
            }
            if (tag.getOrderNumber() > startOrder) {
                int newOrderNumber = tag.getOrderNumber() - 1;
                tag.setOrderNumber(newOrderNumber);
            }
        }
    }

    @Override
    public boolean hasItem(HtmlNode item) {
        return items.contains(item);
    }

    public List<MediaQuery> getMediaQueryList() {
        return mediaQueryList;
    }

    public void setMediaQueryList(List<MediaQuery> mediaQueryList) {
        this.mediaQueryList = mediaQueryList;
    }

    public HtmlProject addMediaQuery(MediaQuery value) {
        mediaQueryList.add(value);
        value.setHtmlProject(this);
        return this;
    }

    public HtmlProject addKeyFrame(KeyFrame value) {
        keyFrameList.add(value);
        value.setHtmlProject(this);
        return this;
    }

    public HtmlProject removeKeyFrame(KeyFrame value) {
        keyFrameList.remove(value);
        return this;
    }

    public HtmlProject removeCssValue(MediaQuery value) {
        mediaQueryList.remove(value);

        return this;
    }


}
