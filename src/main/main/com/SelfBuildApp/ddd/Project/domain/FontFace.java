package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "font_face" )
public class FontFace implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Column(name = "id", unique = true)
    @JsonView(PropertyAccess.Details.class)
    private Long id;

    @Version
    protected Long version;

    @NotEmpty()
    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String name;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIgnore()
    private HtmlProject project;

    @Transient
    @JsonIgnore
    private PathFileManager pathFileManager;

    @OneToMany(mappedBy = "fontFace", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonView({PropertyAccess.HtmlTagDetails.class, PropertyAccess.Details.class})
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<AssetProject> src;

    public FontFace() {
        src = new ArrayList<>();
    }

    public void setPathFileManager(PathFileManager pathFileManager) {
        this.pathFileManager = pathFileManager;
    }

    public PathFileManager getPathFileManager() {
        if (pathFileManager == null) {

            return project.getPathFileManager();
        }
        return pathFileManager;
    }

    @PostRemove
    public void postRemove() {
        for (AssetProject asset: src ) {
            asset.deleteResource();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HtmlProject getProject() {
        return project;
    }

    public void setProject(HtmlProject project) {
        this.project = project;
    }

    public List<AssetProject> getSrc() {
        return src;
    }

    public void setSrc(List<AssetProject> src) {
        this.src = src;
    }



    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
