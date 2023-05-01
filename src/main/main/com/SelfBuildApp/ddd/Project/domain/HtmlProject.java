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
import java.util.*;

@Entity
@Table( name = "html_project" )
@JsonDeserialize()
@SqlResultSetMapping(
        name = "HtmlProjectMapping",
        classes = {
                @ConstructorResult(
                        targetClass = HtmlProject.class,
                        columns = {
                                @ColumnResult(name =  "projectId"),
                                @ColumnResult(name =  "projectPageUrl"),
                                @ColumnResult(name =  "projectName"),
                                @ColumnResult(name =  "projectVersion", type = Long.class),
                        }),
        }
)
@SqlResultSetMapping(
        name = "HtmlNodeMapping",
        classes = {

                @ConstructorResult(
                        targetClass = HtmlTag.class,
                        columns = {
                                @ColumnResult(name =  "node_id"),
                                @ColumnResult(name =  "node_parent_id"),
                                @ColumnResult(name =  "node_short_uuid"),
                                @ColumnResult(name =  "node_text"),
                                @ColumnResult(name =  "node_version", type = Long.class),
                                @ColumnResult(name =  "node_order_number"),
                                @ColumnResult(name =  "node_dtype"),
                                @ColumnResult(name =  "node_tag_name"),
                                @ColumnResult(name =  "node_attrs"),
                                @ColumnResult(name =  "node_closing_tag", type = String.class),
                                @ColumnResult(name =  "node_svg_content"),
                                @ColumnResult(name =  "node_resource_filename"),
                                @ColumnResult(name =  "node_resource_file_extension"),
                                @ColumnResult(name =  "node_resource_url")
                        }),

        }


)
@SqlResultSetMapping(
        name = "CssStyleMapping",
        classes = {
                @ConstructorResult(
                        targetClass = CssStyle.class,
                        columns = {
                                @ColumnResult(name =  "cssS_id", type = Long.class),
                                @ColumnResult(name =  "cssS_css_identity"),
                                @ColumnResult(name =  "cssS_multiple_value", type = String.class),
                                @ColumnResult(name =  "cssS_name"),
                                @ColumnResult(name =  "cssS_unit_name"),
                                @ColumnResult(name =  "cssS_unit_name_second"),
                                @ColumnResult(name =  "cssS_unit_name_third"),
                                @ColumnResult(name =  "cssS_unit_name_fourth"),
                                @ColumnResult(name =  "cssS_unit_name_fifth"),
                                @ColumnResult(name =  "cssS_value"),
                                @ColumnResult(name =  "cssS_value_second"),
                                @ColumnResult(name =  "cssS_value_third"),
                                @ColumnResult(name =  "cssS_value_fourth"),
                                @ColumnResult(name =  "cssS_value_fifth"),
                                @ColumnResult(name =  "cssS_html_tag_id"),
                                @ColumnResult(name =  "cssS_media_query_id", type = Long.class),
                                @ColumnResult(name =  "cssS_parent_id", type = Long.class),
                                @ColumnResult(name =  "cssS_pseudo_selector_id", type = Long.class),
                                @ColumnResult(name =  "cssS_resource_filename"),
                                @ColumnResult(name =  "cssS_resource_file_extension"),
                                @ColumnResult(name =  "cssS_resource_url")
                        }),
        }
)
@SqlResultSetMapping(
        name = "FontFaceMapping",
        classes = {
                @ConstructorResult(
                        targetClass = FontFace.class,
                        columns = {
                                @ColumnResult(name =  "fontFace_id", type = Long.class),
                                @ColumnResult(name =  "fontFace_version", type = Long.class),
                                @ColumnResult(name =  "fontFace_name"),
                        }),
        }
)
@SqlResultSetMapping(
        name = "KeyFrameMapping",
        classes = {
                @ConstructorResult(
                        targetClass = KeyFrame.class,
                        columns = {
                                @ColumnResult(name =  "keyFrame_id"),
                                @ColumnResult(name =  "keyFrame_name"),
                        }),
        }
)
@SqlResultSetMapping(
        name = "AssetProjectMapping",
        classes = {
                @ConstructorResult(
                        targetClass = AssetProject.class,
                        columns = {
                                @ColumnResult(name =  "assetProject_id", type = Long.class),
                                @ColumnResult(name =  "assetProject_font_face_id", type = Long.class),
                                @ColumnResult(name =  "assetProject_type", type = int.class),
                                @ColumnResult(name =  "assetProject_format"),
                                @ColumnResult(name =  "assetProject_resource_filename"),
                                @ColumnResult(name =  "assetProject_resource_file_extension"),
                                @ColumnResult(name =  "assetProject_resource_url")
                        }),
        }
)
@SqlResultSetMapping(
        name = "MediaQueryMapping",
        classes = {
                @ConstructorResult(
                    targetClass = MediaQuery.class,
                    columns = {
                            @ColumnResult(name =  "mediaQuery_id", type = Long.class),
                            @ColumnResult(name =  "mediaQuery_name"),
                            @ColumnResult(name =  "mediaQuery_color"),
                            @ColumnResult(name =  "mediaQuery_color_unit_name"),
                    }),
        }
)
@SqlResultSetMapping(
        name = "PseudoSelectorMapping",
        classes = {
                @ConstructorResult(
                    targetClass = PseudoSelector.class,
                    columns = {
                            @ColumnResult(name =  "pseudoSelector_id", type = Long.class),
                            @ColumnResult(name =  "pseudoSelector_name"),
                            @ColumnResult(name =  "pseudoSelector_value"),
                            @ColumnResult(name =  "pseudoSelector_unit_name"),
                            @ColumnResult(name =  "pseudoSelector_delimiter"),
                            @ColumnResult(name =  "pseudoSelector_html_tag_id"),
                            @ColumnResult(name =  "pseudoSelector_media_query_id", type = Long.class),
                            @ColumnResult(name =  "pseudoSelector_key_frame_id")
                    }),
        }
)
@SqlResultSetMapping(
        name = "CssValueMapping",
        classes = {
                @ConstructorResult(
                    targetClass = CssValue.class,
                    columns = {
                            @ColumnResult(name =  "cssV_id", type = Long.class),
                            @ColumnResult(name =  "cssV_css_identity"),
                            @ColumnResult(name =  "cssV_inset", type = Boolean.class),
                            @ColumnResult(name =  "cssV_special_val_gradient", type = Boolean.class),
                            @ColumnResult(name =  "cssV_unit_name"),
                            @ColumnResult(name =  "cssV_unit_name_second"),
                            @ColumnResult(name =  "cssV_unit_name_third"),
                            @ColumnResult(name =  "cssV_unit_name_fourth"),
                            @ColumnResult(name =  "cssV_unit_name_fifth"),
                            @ColumnResult(name =  "cssV_unit_name_sixth"),
                            @ColumnResult(name =  "cssV_unit_name_seventh"),
                            @ColumnResult(name =  "cssV_unit_name_eighth"),
                            @ColumnResult(name =  "cssV_unit_name_ninth"),
                            @ColumnResult(name =  "cssV_value"),
                            @ColumnResult(name =  "cssV_value_second"),
                            @ColumnResult(name =  "cssV_value_third"),
                            @ColumnResult(name =  "cssV_value_fourth"),
                            @ColumnResult(name =  "cssV_value_fifth"),
                            @ColumnResult(name =  "cssV_value_sixth"),
                            @ColumnResult(name =  "cssV_value_seventh"),
                            @ColumnResult(name =  "cssV_value_eighth"),
                            @ColumnResult(name =  "cssV_value_ninth"),
                            @ColumnResult(name =  "cssV_media_query_id", type = Long.class),
                            @ColumnResult(name =  "cssV_css_style_id", type = Long.class)
                    }),
        }
)
public class HtmlProject extends Project<HtmlNode> implements Serializable {



    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("orderNumber")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonView({PropertyAccess.HtmlTagDetails.class})
    protected Set<HtmlNode> items;

    @Valid
    @OneToMany(mappedBy = "htmlProject", cascade = CascadeType.ALL,
             orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<MediaQuery> mediaQueryList;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<AssetProject> assets;


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonView(PropertyAccess.Details.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    protected List<FontFace> fontFaceList;


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

    @Transient
    @JsonIgnore
    public Map<String, HtmlNode> tagsMap = new HashMap<>();

    @Transient
    @JsonIgnore
    public Map<Long, CssStyle> cssMap = new HashMap<>();

    @Transient
    @JsonIgnore
    public Map<Long, CssValue> cssValueMap = new HashMap<>();

    @Transient
    @JsonIgnore
    public Map<String, KeyFrame> keyFramesMap = new HashMap<>();

    @Transient
    @JsonIgnore
    public Map<Long, MediaQuery> mediaQueryMap = new HashMap<>();

    @Transient
    @JsonIgnore
    public Map<Long, FontFace> fontFaceMap = new HashMap<>();

    @Transient
    @JsonIgnore
    public Map<Long, AssetProject> assetMap = new HashMap<>();

    @Transient
    @JsonIgnore
    public Map<Long, PseudoSelector> pseudoSelectorMap = new HashMap<>();

    public HtmlProject(String projectId) {
        this();
        id = projectId;
    }

    public HtmlProject(String projectId, String projectPageUrl, String projectName, Long projectVersion) {
        this();
        id = projectId;
        pageUrl = projectPageUrl;
        name = projectName;
        version = projectVersion;
    }

    public HtmlProject() {
        items = new HashSet<>();
        mediaQueryList = new ArrayList<>();
        keyFrameList = new ArrayList<>();
        assets = new ArrayList<>();
        fontFaceList = new ArrayList<>();
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

    public List<KeyFrame> getKeyFrameList() {
        return keyFrameList;
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

    public HtmlProject addFontFace(FontFace value) {
        fontFaceList.add(value);
        value.setProject(this);
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

    public List<FontFace> getFontFaceList() {
        return fontFaceList;
    }

    public void setFontFaceList(List<FontFace> fontFaceList) {
        this.fontFaceList = fontFaceList;
    }

    public List<AssetProject> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetProject> assets) {
        this.assets = assets;
    }
}
