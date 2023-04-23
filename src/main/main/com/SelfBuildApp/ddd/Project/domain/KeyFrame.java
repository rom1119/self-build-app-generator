package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.ValueGenerator;
import com.SelfBuildApp.ddd.Project.domain.Unit.BaseUnit;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.BaseAggregateRoot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "key_frame" )
@NamedNativeQuery(name = "KeyFrame.findAllForProjectId", query = "SELECT * FROM key_frame where html_project_id=?;", resultClass = KeyFrame.class)
public class KeyFrame extends BaseAggregateRoot implements Serializable {

    @NotEmpty()
    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String name;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "html_project_id")
    @JsonIgnore()
    private HtmlProject htmlProject;

    @Valid
    @OneToMany(mappedBy = "keyFrame", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<PseudoSelector> selectorList;

    @Transient
    @JsonIgnore
    private PathFileManager pathFileManager;

    public KeyFrame(
            String keyFrame_id,
            String keyFrame_name
    ) {

        this();
        this.id = keyFrame_id;
        this.name = keyFrame_name;
    }



    public void setPathFileManager(PathFileManager pathFileManager) {
        this.pathFileManager = pathFileManager;
    }

    public PathFileManager getPathFileManager() {
        if (pathFileManager == null) {
            return htmlProject.getPathFileManager();
        }
        return pathFileManager;
    }

    public KeyFrame() {
        selectorList = new ArrayList<>();
    }

    @JsonIgnore
    public String getFullValue() throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(generateBaseValue());

        return stringBuilder.toString();
    }

    private String generateBaseValue() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();





        return stringBuilder.toString();

    }


    private String getUnitNameFromName(String name)
    {
        return ValueGenerator.getUnitNameFromName(name);
    }

    private BaseUnit getUnitFromNameAndValue(String name, String value) throws Exception {
        return ValueGenerator.getUnitFromNameAndValue(name, value);
    }



    public boolean hasSelector(PseudoSelector cssStyle) {

        return selectorList.contains(cssStyle);
    }


    public KeyFrame addSelector(PseudoSelector value) {
        selectorList.add(value);
        value.setKeyFrame(this);
        return this;
    }

    public KeyFrame removeSelector(PseudoSelector value) {
        selectorList.remove(value);

        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HtmlProject getHtmlProject() {
        return htmlProject;
    }

    public void setHtmlProject(HtmlProject htmlProject) {
        this.htmlProject = htmlProject;
    }

    public List<PseudoSelector> getSelectorList() {
        return selectorList;
    }

    public void setSelectorList(List<PseudoSelector> selectorList) {
        this.selectorList = selectorList;
    }
}
