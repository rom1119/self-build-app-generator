package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.ddd.Project.Project;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
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

    public HtmlProject() {
        items = new HashSet<>();
    }

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty("items")
    public Set<HtmlNode> getMainItems()
    {
        Set<HtmlNode> list = new HashSet<>();
        for (HtmlNode item: items) {
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
}
