package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.Storage.FileInterface;
import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.infrastructure.Validation.FileConstrain;
import com.SelfBuildApp.infrastructure.Validation.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table( name = "asset_project" )
public class AssetProject extends DtoFontFile implements Serializable, FileInterface {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Column(name = "id", unique = true)
    @JsonView(PropertyAccess.Details.class)
    private Long id;

    @Transient
    protected Map<Integer, String> typesMap;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private int type;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String resourceUrl;

    @JsonIgnore()
    private String resourceFilename;

    @JsonIgnore()
    private String resourceFileExtension;

    @JsonView(PropertyAccess.Details.class)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String format;

    @Transient
    @JsonIgnore
    private PathFileManager pathFileManager;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore()
    private HtmlProject project;

    @ManyToOne( fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "font_face_id")
    @JsonIgnore()
    private FontFace fontFace;

    @FileConstrain(size = 30000)
    @Transient
    protected MultipartFile file;


    public AssetProject() {
        this.typesMap = new HashMap<>();
        typesMap.put(1, "font");
        typesMap.put(2, "img");
        typesMap.put(3, "css");
        typesMap.put(4, "js");
    }

    @PostRemove
    public void postRemove() {
        deleteResource();
    }

    @JsonIgnore
    public PathFileManager getPathFileManager() {
        if (pathFileManager == null) {
            if (project != null) {
                if (project.getPathFileManager() != null) {
                    return project.getPathFileManager();

                }

            }

            if (fontFace != null) {
                return fontFace.getPathFileManager();

            }
        }
        return pathFileManager;
    }

    public void setPathFileManager(PathFileManager pathFileManager) {
        this.pathFileManager = pathFileManager;
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

        AssetProject asset = (AssetProject) o;

        if (!getId().equals(asset.getId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getType() + getId().intValue();
        result = 3 * result + (getResourceFileExtension() != null ? getResourceFileExtension().trim().hashCode() : 5);

        result = 5 * result + (getResourceUrl() != null ? getResourceUrl().trim().hashCode() : 5);
        result = 9 * result + (getResourceFilename() != null ? getResourceFilename().trim().hashCode() : 0);

        return result;
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
        String filename = Integer.toHexString(hashCode() +hashCode() );
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

    protected String getNamedType()
    {
        return typesMap.get(type);
    }

    @Override
    @JsonIgnore
    public String getFileName() {
        return resourceFilename + "." + resourceFileExtension;
    }

    @Override
    @JsonIgnore
    public String getDirName() {
        StringBuilder dir = new StringBuilder();
        dir.append("project/");
        dir.append(project.getId());
        dir.append("/assets/");
        dir.append(getNamedType());
        dir.append(getId());
        dir.append("/");

        return dir.toString();
    }


    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String UPLOAD_DIR() throws Exception {
        StringBuilder dir = new StringBuilder();
        if (getPathFileManager() == null) {
            throw new Exception("pathFileManager is null");
        }
        dir.append(getPathFileManager().getBaseUploadDir());
        dir.append("project/");
        if (project != null) {
            dir.append(project.getId());
        }
        dir.append("/assets/fonts/");
        dir.append(getNamedType());
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
        if (project != null) {
            dir.append(project.getId());
        }
        dir.append("/assets/fonts/");
        dir.append(getNamedType());
        dir.append(getId());
        dir.append("/");

        return dir.toString();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonIgnore()
    public HtmlProject getProject() {
        return project;
    }

    public void setProject(HtmlProject project) {
        this.project = project;
    }

    @JsonIgnore()
    public FontFace getFontFace() {
        return fontFace;
    }

    public void setFontFace(FontFace fontFace) {
        this.fontFace = fontFace;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }


}
