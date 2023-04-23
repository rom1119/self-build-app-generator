package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.ddd.Project.infrastructure.repo.HtmlAttrConverter;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TextNode extends HtmlNode {

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @Column(length = 15000, columnDefinition="Text")
    protected String text;

    public TextNode() {
    }

    public TextNode(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public TextNode(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(value = "isTextNode")
    public boolean isTextNode() {
        return true;
    }

    public void setText(String text) {
        this.text = text;
    }
}
