package com.Self.Build.App.ddd.Project.domain;

import com.Self.Build.App.ddd.Project.Project;
import com.Self.Build.App.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table( name = "html_project" )
@JsonDeserialize()
public class HtmlProject extends Project<HtmlTag> implements Serializable {

    @OneToMany(mappedBy = "project")
    @Fetch(FetchMode.SELECT)
    @OrderBy("orderNumber")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Set<HtmlTag> items;

    public HtmlProject() {
        items = new HashSet<>();
    }

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty("items")
    public Set<HtmlTag> getMainItems()
    {
        Set<HtmlTag> list = new HashSet<>();
        for (HtmlTag item: items) {
            if (!item.hasParent()) {
                list.add(item);
            }
        }

        return list;
    }

    public Project<HtmlTag> appendChild(HtmlTag item) {
        int firsttLvlCount = 1;
        for (HtmlTag itemEl: items) {
            if (!itemEl.hasParent()) {
                firsttLvlCount++;
            }
        }
//        int orderNumber =
        addItem(item);
        item.setOrderNumber(firsttLvlCount);
        return this;
    }

    public Project<HtmlTag> addItem(HtmlTag item) {
        items.add(item);
        item.setProject(this);
        return this;
    }

    @Override
    public Project<HtmlTag> removeItem(HtmlTag item) {
        items.remove(item);
        item.setProject(null);
        recalculateOrders(item.getOrderNumber());

        return this;
    }

    private void recalculateOrders(int startOrder)
    {
        for(HtmlTag tag : items) {
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
    public boolean hasItem(HtmlTag item) {
        return items.contains(item);
    }
}
