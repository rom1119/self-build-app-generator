package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.*;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HtmlProjectRepository extends GenericJpaRepository<HtmlProject> {



    public HtmlProject fetchFullProjectData( String projectId)
    {

//        EntityGraph<HtmlProject> entityGraph = entityManager.createEntityGraph(HtmlProject.class);


//        Query namedQuery = this.entityManager.createQuery( "SELECT p from HtmlProject p "
//                + " LEFT JOIN FETCH p.fontFaceList fontFaceList"
//
//                        + " LEFT JOIN FETCH p.items nodes "
//                        + " LEFT JOIN FETCH p.mediaQueryList mediaQueryList"
//                + " LEFT JOIN FETCH mediaQueryList.pseudoSelectors pseudoSMediaQuery "
//                + " LEFT JOIN FETCH mediaQueryList.cssStyleList cssSMediaQuery "
//                + " LEFT JOIN FETCH mediaQueryList.cssValues cssVMediaQuery "
//
//                + " LEFT JOIN FETCH p.keyFrameList keyFrameList"
//                + " LEFT JOIN FETCH keyFrameList.selectorList pseudoSKeyFrame"

//                + " LEFT JOIN FETCH nodes.cssStyleList cssStyleListNode "
//                + " LEFT JOIN FETCH cssStyleListNode.children cssStyleChildListNode "
//                + " LEFT JOIN FETCH cssStyleChildListNode.cssValues cssValueChildListNode "
//                + " LEFT JOIN FETCH cssStyleListNode.cssValues cssValueListNode "
//                + " LEFT JOIN FETCH nodes.pseudoSelectors pseudoSelectorsNode "

//                + " LEFT JOIN FETCH nodes.children childNode "
//                + " LEFT JOIN FETCH childNode.cssStyleList cssStyleListChildNode "
//                + " LEFT JOIN FETCH cssStyleListChildNode.children cssStyleChildListChildNode "
//                + " LEFT JOIN FETCH cssStyleListChildNode.cssValues cssValueChildListChildNode "
//                + " LEFT JOIN FETCH cssStyleChildListChildNode.cssValues cssValueListChildNode "
//                + " LEFT JOIN FETCH childNode.pseudoSelectors pseudoSelectorsChildNode "

//        +  " where p.id = :id"
//                , clazz);

//        entityGraph.addAttributeNodes("fontFaceList");
//
//
//        entityGraph.addSubgraph("mediaQueryList")
//                .addAttributeNodes("pseudoSelectors", "cssStyleList", "cssValues");
//        entityGraph.addSubgraph("keyFrameList").addAttributeNodes("selectorList");
//        TypeReferenced<List> a = new TypeReferenced<List>(){};

//        Subgraph<HtmlTag> itemsSubgraph = entityGraph.addSubgraph("items",  HtmlTag.class);
//        Subgraph<TextNode> itemsTextSubgraph = entityGraph.addSubgraph("items",  TextNode.class);
//                    .addSubgraph("cssStyleList")
//                        .addSubgraph("children")
//                            .addSubgraph("cssValues")
//                                .addSubgraph("cssValues");
//        cssStyleListSubgraph.addAttributeNodes("pseudoSelectors");

//        entityGraph
//                .addAttributeNodes(
//                        "children",
//                        "cssStyleList"
//
//                );




//        namedQuery.setHint("javax.persistence.loadgraph", entityGraph);

//        namedQuery.setParameter("id", projectId);
//        namedQuery.setHint(QueryHints.PASS_DISTINCT_THROUGH, false);

//        HtmlProject singleResult = (HtmlProject) namedQuery.getSingleResult();
//        System.out.println("NEXT_QUERY");
//        HtmlProject result = fetchNextFields(singleResult,
//                 " LEFT JOIN p.items nodes "
//                        );
//
//        System.out.println("NEXT_QUERY 22222");
//        result = fetchNextFields(result,
//                 " LEFT JOIN FETCH p.mediaQueryList mediaQueryList "
//                        );
//
//        System.out.println("NEXT_QUERY 3333");
//        result = fetchNextFields(result,
//                 " LEFT JOIN FETCH p.keyFrameList keyFrameList "
//                        );
//
//        System.out.println("NEXT_QUERY 444444");
//        result = fetchNextFields(result,
//                 " LEFT JOIN FETCH p.assets assets "
//                        );
//
////        System.out.println("NEXT_QUERY 55555");
////        result = fetchNextFields(singleResult,
////                 " LEFT JOIN FETCH p.pseudoSelectors pseudoSelectors "
////                        );
//
//        System.out.println("NEXT_QUERY 55555");
//        result = fetchNextFields(result,
//                 " LEFT JOIN FETCH p.items nodes "
////                 + " LEFT JOIN FETCH nodes.children nodesChildren "
//                 + " LEFT JOIN FETCH nodes.cssStyleList cssStyleList "
////                 + " LEFT JOIN FETCH nodes.pseudoSelectors pseudoSelectorsNodes "
////                 + " LEFT JOIN FETCH nodesChildren.pseudoSelectors pseudoSelectorsNodesChildren "
//
//        );
//
//        result = fetchNextFields(result,
//                 " LEFT JOIN FETCH p.items nodes "
////                 + " LEFT JOIN FETCH nodes.children nodesChildren "
////                 + " LEFT JOIN FETCH nodes.cssStyleList cssStyleList "
//                 + " LEFT JOIN FETCH cssStyleList.cssValues cssValues "
////                 + " LEFT JOIN FETCH nodes.pseudoSelectors pseudoSelectorsNodes "
////                 + " LEFT JOIN FETCH nodesChildren.pseudoSelectors pseudoSelectorsNodesChildren "
//
//        );
//
//        System.out.println("NEXT_QUERY 666666");
//        result = fetchNextFields(result,
//                 " LEFT JOIN FETCH p.items nodes "
////                 + " LEFT JOIN FETCH nodes.children nodesChildren "
////                 + " LEFT JOIN FETCH nodes.cssStyleList cssStyleList "
//                 + " LEFT JOIN FETCH nodes.pseudoSelectors pseudoSelectorsNodes "
////                 + " LEFT JOIN FETCH nodesChildren.pseudoSelectors pseudoSelectorsNodesChildren "
//
//        );
//
//        System.out.println("NEXT_QUERY 77777");
//        result = fetchNextFields(result,
//                 " LEFT JOIN FETCH p.items nodes "
//                 + " LEFT JOIN FETCH nodes.children nodesChildren "
////                 + " LEFT JOIN FETCH nodes.cssStyleList cssStyleList "
////                 + " LEFT JOIN FETCH nodes.pseudoSelectors pseudoSelectorsNodes "
////                 + " LEFT JOIN FETCH nodesChildren.pseudoSelectors pseudoSelectorsNodesChildren "
//
//        );



//        result = fetchNextFields(result,
//                " LEFT JOIN FETCH p.items nodes "
//                        + " LEFT JOIN FETCH nodes.cssStyleList cssStyleListNode "
//                        + " LEFT JOIN FETCH cssStyleListNode.children cssStyleChildListNode "
//                        + " LEFT JOIN FETCH cssStyleChildListNode.cssValues cssValueChildListNode "
//                        + " LEFT JOIN FETCH cssStyleListNode.cssValues cssValueListNode "
//                        + " LEFT JOIN FETCH nodes.pseudoSelectors pseudoSelectorsNode ");

//        result = fetchNextFields(result,
//                        " LEFT JOIN FETCH p.mediaQueryList mediaQueryList"
//                       );

//        Query namedQuery = this.entityManager.createQuery( "SELECT cssStyle from CssStyle cssStyle "
////                        + " LEFT JOIN FETCH p.fontFaceList fontFaceList"
////
//                        + " LEFT JOIN FETCH cssStyle.cssValues cssValues "
//                        + " LEFT JOIN FETCH cssStyle.parent parent "
//                        + " LEFT JOIN FETCH cssStyle.pseudoSelector pseudoSelector "
//                        + " LEFT JOIN FETCH cssStyle.mediaQuery mediaQuery "
//                        + " LEFT JOIN FETCH cssStyle.htmlTag htmlTag "
//
//                        + " LEFT JOIN FETCH htmlTag.parent htmlTagParent "
//                        + " LEFT JOIN FETCH htmlTag.project project"
//                    + " LEFT JOIN FETCH project.mediaQueryList mediaQueryList "
//                    + " LEFT JOIN FETCH mediaQueryList.pseudoSelectors pseudoSMediaQuery "
//                + " LEFT JOIN FETCH mediaQueryList.cssStyleList cssSMediaQuery "
//                + " LEFT JOIN FETCH mediaQueryList.cssValues cssVMediaQuery "
//
//                + " LEFT JOIN FETCH project.keyFrameList keyFrameList"
//                + " LEFT JOIN FETCH keyFrameList.selectorList pseudoSKeyFrame"
//                + " LEFT JOIN FETCH project.assets assets "
//               + " LEFT JOIN FETCH project.keyFrameList keyFrameList "

//
//                + " LEFT JOIN FETCH nodes.cssStyleList cssStyleListNode "
//                + " LEFT JOIN FETCH cssStyleListNode.children cssStyleChildListNode "
//                + " LEFT JOIN FETCH cssStyleChildListNode.cssValues cssValueChildListNode "
//                + " LEFT JOIN FETCH cssStyleListNode.cssValues cssValueListNode "
//                + " LEFT JOIN FETCH nodes.pseudoSelectors pseudoSelectorsNode "
//
//                + " LEFT JOIN FETCH nodes.children childNode "
//                + " LEFT JOIN FETCH childNode.cssStyleList cssStyleListChildNode "
//                + " LEFT JOIN FETCH cssStyleListChildNode.children cssStyleChildListChildNode "
//                + " LEFT JOIN FETCH cssStyleListChildNode.cssValues cssValueChildListChildNode "
//                + " LEFT JOIN FETCH cssStyleChildListChildNode.cssValues cssValueListChildNode "
//                + " LEFT JOIN FETCH childNode.pseudoSelectors pseudoSelectorsChildNode "

//                        +  " where project.id = :id"
//                , CssStyle.class);

        Query namedQueryNodes = this.entityManager.createNativeQuery(
                "SELECT" +
//                        " p.id projectId, p.page_url projectPageUrl " +
//                " ,fontFace.id fontFace_id, fontFace.version fontFace_version, fontFace.name fontFace_name" +
//                " ,keyFrame.id keyFrame_id, keyFrame.name keyFrame_name" +
//                " ,assetProject.id assetProject_id, assetProject.font_face_id assetProject_font_face_id, assetProject.type assetProject_type, assetProject.format assetProject_format, assetProject.resource_file_extension assetProject_resource_file_extension, assetProject.resource_filename assetProject_resource_filename, assetProject.resource_url assetProject_resource_url" +
//                " ,mediaQuery.id mediaQuery_id, mediaQuery.name mediaQuery_name, mediaQuery.color mediaQuery_color, mediaQuery.color_unit_name mediaQuery_color_unit_name" +
//                " ,pseudoSelector.id pseudoSelector_id, pseudoSelector.name pseudoSelector_name, pseudoSelector.value pseudoSelector_value, pseudoSelector.unit_name pseudoSelector_unit_name, pseudoSelector.delimiter pseudoSelector_delimiter, pseudoSelector.html_tag_id pseudoSelector_html_tag_id, pseudoSelector.media_query_id pseudoSelector_media_query_id, pseudoSelector.key_frame_id pseudoSelector_key_frame_id" +
                " node.id node_id, node.dtype node_dtype, node.version node_version, node.order_number node_order_number, node.short_uuid node_short_uuid, node.text node_text, node.attrs node_attrs, node.closing_tag node_closing_tag, node.resource_file_extension node_resource_file_extension, node.resource_filename node_resource_filename, node.resource_url node_resource_url, node.svg_content node_svg_content, node.tag_name node_tag_name, node.parent_id node_parent_id, node.project_id node_project_id " +
//                " ,nodeChild.id nodeChild_id, nodeChild.dtype nodeChild_dtype, nodeChild.version nodeChild_version, nodeChild.order_number nodeChild_order_number, nodeChild.short_uuid nodeChild_short_uuid, nodeChild.text nodeChild_text, nodeChild.attrs nodeChild_attrs, nodeChild.closing_tag nodeChild_closing_tag, nodeChild.resource_file_extension nodeChild_resource_file_extension, nodeChild.resource_filename nodeChild_resource_filename, nodeChild.resource_url nodeChild_resource_url, nodeChild.svg_content nodeChild_svg_content, nodeChild.tag_name nodeChild_tag_name, nodeChild.parent_id nodeChild_parent_id, nodeChild.project_id nodeChild_project_id " +
//                " ,cssS.id cssS_id, cssS.css_identity cssS_css_identity, cssS.multiple_value cssS_multiple_value, cssS.name cssS_name, cssS.resource_file_extension cssS_resource_file_extension, cssS.resource_filename cssS_resource_filename, cssS.resource_url cssS_resource_url, cssS.unit_name cssS_unit_name, cssS.unit_name_second cssS_unit_name_second, cssS.unit_name_third cssS_unit_name_third, cssS.unit_name_fourth cssS_unit_name_fourth, cssS.unit_name_fifth cssS_unit_name_fifth, cssS.value cssS_value, cssS.value_second cssS_value_second, cssS.value_third cssS_value_third, cssS.value_fourth cssS_value_fourth, cssS.value_fifth cssS_value_fifth, cssS.html_tag_id cssS_html_tag_id, cssS.media_query_id cssS_media_query_id, cssS.parent_id cssS_parent_id, cssS.pseudo_selector_id cssS_pseudo_selector_id " +
//                " ,cssSChild.id cssSChild_id, cssSChild.css_identity cssSChild_css_identity, cssSChild.multiple_value cssSChild_multiple_value, cssSChild.name cssSChild_name, cssSChild.resource_file_extension cssSChild_resource_file_extension, cssSChild.resource_filename cssSChild_resource_filename, cssSChild.resource_url cssSChild_resource_url, cssSChild.unit_name cssSChild_unit_name, cssSChild.unit_name_second cssSChild_unit_name_second, cssSChild.unit_name_third cssSChild_unit_name_third, cssSChild.unit_name_fourth cssSChild_unit_name_fourth, cssSChild.unit_name_fifth cssSChild_unit_name_fifth, cssSChild.value cssSChild_value, cssSChild.value_second cssSChild_value_second, cssSChild.value_third cssSChild_value_third, cssSChild.value_fourth cssSChild_value_fourth, cssSChild.value_fifth cssSChild_value_fifth, cssSChild.html_tag_id cssSChild_html_tag_id, cssSChild.media_query_id cssSChild_media_query_id, cssSChild.parent_id cssSChild_parent_id, cssSChild.pseudo_selector_id cssSChild_pseudo_selector_id " +
//                " ,cssV.id cssV_id, cssV.css_identity cssV_css_identity, cssV.inset cssV_inset, cssV.special_val_gradient cssV_special_val_gradient, cssV.unit_name cssV_unit_name, cssV.unit_name_second cssV_unit_name_second, cssV.unit_name_third cssV_unit_name_third, cssV.unit_name_fourth cssV_unit_name_fourth, cssV.unit_name_fifth cssV_unit_name_fifth, cssV.unit_name_sixth cssV_unit_name_sixth, cssV.unit_name_seventh cssV_unit_name_seventh, cssV.unit_name_eighth cssV_unit_name_eighth, cssV.unit_name_ninth cssV_unit_name_ninth, cssV.value cssV_value, cssV.value_second cssV_value_second, cssV.value_third cssV_value_third, cssV.value_fourth cssV_value_fourth, cssV.value_fifth cssV_value_fifth, cssV.value_sixth cssV_value_sixth, cssV.value_seventh cssV_value_seventh, cssV.value_eighth cssV_value_eighth, cssV.value_ninth cssV_value_ninth, cssV.css_style_id cssV_css_style_id, cssV.media_query_id cssV_media_query_id " +
                "from html_project p "
//                + " LEFT JOIN font_face fontFace ON p.id = fontFace.project_id"
//                + " LEFT JOIN asset_project assetProject ON fontFace.id = assetProject.font_face_id"
//                        + " LEFT JOIN  key_frame keyFrame ON p.id = keyFrame.html_project_id"
//                        + " LEFT JOIN  media_query mediaQuery ON ( p.id = mediaQuery.html_project_id)"
                        + " LEFT JOIN  html_node node ON p.id = node.project_id "
//                        + " LEFT JOIN  pseudo_selector pseudoSelector ON ( node.id = pseudoSelector.html_tag_id OR keyFrame.id = pseudoSelector.key_frame_id OR mediaQuery.id = pseudoSelector.media_query_id)"
//                        + " LEFT JOIN  html_node nodeChild ON node.id = nodeChild.parent_id"
//                        + " LEFT JOIN  pseudo_selector pseudoSHtmlTag ON ( node.id = pseudoSHtmlTag.html_tag_id)"
//                        + " LEFT JOIN  pseudo_selector pseudoSHtmlTagChild ON ( nodeChild.id = pseudoSHtmlTagChild.html_tag_id)"
//                        + " LEFT JOIN  css_style cssS ON (node.id = cssS.html_tag_id OR cssS.id = cssS.parent_id OR cssS.media_query_id = mediaQuery.id OR cssS.pseudo_selector_id = pseudoSelector.id)"
//                        + " LEFT JOIN  css_value cssV ON (cssS.id = cssV.css_style_id OR  cssV.media_query_id = mediaQuery.id)"
//                        + " LEFT JOIN  css_style cssSChild ON cssS.id = cssSChild.parent_id"
//                        + " LEFT JOIN  media_query mediaQCssStyle ON ( cssS.media_query_id = mediaQCssStyle.id)"
//                        + " LEFT JOIN  media_query mediaQCssValue ON ( cssV.media_query_id = mediaQCssValue.id)"
                + " where p.id = :id "
                ,
                "HtmlNodeMapping" )
                ;
        namedQueryNodes.setParameter("id", projectId);
        List<HtmlTag> arrayResultNodes =  namedQueryNodes.getResultList();



        Query namedQueryCssStyle = this.entityManager.createNativeQuery(
                "SELECT " +

                        " cssS.id cssS_id, cssS.css_identity cssS_css_identity, cssS.multiple_value cssS_multiple_value, cssS.name cssS_name, cssS.resource_file_extension cssS_resource_file_extension, cssS.resource_filename cssS_resource_filename, cssS.resource_url cssS_resource_url, cssS.unit_name cssS_unit_name, cssS.unit_name_second cssS_unit_name_second, cssS.unit_name_third cssS_unit_name_third, cssS.unit_name_fourth cssS_unit_name_fourth, cssS.unit_name_fifth cssS_unit_name_fifth, cssS.value cssS_value, cssS.value_second cssS_value_second, cssS.value_third cssS_value_third, cssS.value_fourth cssS_value_fourth, cssS.value_fifth cssS_value_fifth, cssS.html_tag_id cssS_html_tag_id, cssS.media_query_id cssS_media_query_id, cssS.parent_id cssS_parent_id, cssS.pseudo_selector_id cssS_pseudo_selector_id " +
                        " from css_style cssS "

                        + "LEFT JOIN  css_style cssParent ON cssS.parent_id = cssParent.id"
                        + " LEFT JOIN  html_node cssStyleParentNode ON cssParent.html_tag_id = cssStyleParentNode.id"

                        + " LEFT JOIN  pseudo_selector pseudoSelectorParent ON cssParent.pseudo_selector_id = pseudoSelectorParent.id"
                        + " LEFT JOIN  html_node nodePseudoSelCssParent ON nodePseudoSelCssParent.id = pseudoSelectorParent.html_tag_id"
                        + " LEFT JOIN  media_query mediaQuery ON cssS.media_query_id = mediaQuery.id"
                        + " LEFT JOIN  html_node node ON node.id = cssS.html_tag_id "
                        + " LEFT JOIN  pseudo_selector pseudoSelector ON cssS.pseudo_selector_id = pseudoSelector.id"
                        + " LEFT JOIN  key_frame keyFrame ON pseudoSelector.key_frame_id = keyFrame.id"
                        + " LEFT JOIN  html_node nodePseudoSel ON nodePseudoSel.id = pseudoSelector.html_tag_id "
                        + " where mediaQuery.html_project_id = :id OR" +
                        " node.project_id = :id OR " +
                        " keyFrame.html_project_id = :id OR " +
                        " nodePseudoSel.project_id = :id OR" +
                        " cssStyleParentNode.project_id = :id OR" +
                        " nodePseudoSelCssParent.project_id = :id "
                ,
                "CssStyleMapping" )
                ;
        namedQueryCssStyle.setParameter("id", projectId);
        List<CssStyle> arrayResultCssStyle =  namedQueryCssStyle.getResultList();



        Query namedQueryFontFace = this.entityManager.createNativeQuery(
                "SELECT " +

                " fontFace.id fontFace_id, fontFace.version fontFace_version, fontFace.name fontFace_name" +
                        " from font_face fontFace "
                        + " where fontFace.project_id = :id"
                ,
                "FontFaceMapping" )
                ;
        namedQueryFontFace.setParameter("id", projectId);
        List<FontFace> arrayResultFontFace =  namedQueryFontFace.getResultList();


        Query namedQueryKeyFrame = this.entityManager.createNativeQuery(
                "SELECT " +

                " keyFrame.id keyFrame_id, keyFrame.name keyFrame_name" +
                        " from key_frame keyFrame "
                        + " where keyFrame.html_project_id  = :id "
                ,
                "KeyFrameMapping" )
                ;
        namedQueryKeyFrame.setParameter("id", projectId);
        List<KeyFrame> arrayResultKeyFrame =  namedQueryKeyFrame.getResultList();


        Query namedQueryAssetProject = this.entityManager.createNativeQuery(
                "SELECT " +

                " assetProject.id assetProject_id, assetProject.font_face_id assetProject_font_face_id, assetProject.type assetProject_type, assetProject.format assetProject_format, assetProject.resource_file_extension assetProject_resource_file_extension, assetProject.resource_filename assetProject_resource_filename, assetProject.resource_url assetProject_resource_url" +
                        " from asset_project assetProject "
                        + " where assetProject.project_id  = :id "
                ,
                "AssetProjectMapping" )
                ;
        namedQueryAssetProject.setParameter("id", projectId);
        List<AssetProject> arrayResultAssetProject =  namedQueryAssetProject.getResultList();



        Query namedQueryMediaQuery = this.entityManager.createNativeQuery(
                "SELECT " +

                " mediaQuery.id mediaQuery_id, mediaQuery.name mediaQuery_name, mediaQuery.color mediaQuery_color, mediaQuery.color_unit_name mediaQuery_color_unit_name" +
                        " from media_query mediaQuery "
                        + " where mediaQuery.html_project_id  = :id "
                ,
                "MediaQueryMapping" )
                ;
        namedQueryMediaQuery.setParameter("id", projectId);
        List<MediaQuery> arrayResultMediaQuery =  namedQueryMediaQuery.getResultList();



        Query namedQueryPseudoSelector = this.entityManager.createNativeQuery(
                "SELECT " +

                " pseudoSelector.id pseudoSelector_id, pseudoSelector.name pseudoSelector_name, pseudoSelector.value pseudoSelector_value, pseudoSelector.unit_name pseudoSelector_unit_name, pseudoSelector.delimiter pseudoSelector_delimiter, pseudoSelector.html_tag_id pseudoSelector_html_tag_id, pseudoSelector.media_query_id pseudoSelector_media_query_id, pseudoSelector.key_frame_id pseudoSelector_key_frame_id" +
                        " from pseudo_selector pseudoSelector "
                        + " LEFT JOIN  media_query mediaQuery ON pseudoSelector.media_query_id = mediaQuery.id"
                        + " LEFT JOIN  html_node node ON node.id = pseudoSelector.html_tag_id "
                        + " LEFT JOIN  key_frame keyFrame ON keyFrame.id = pseudoSelector.key_frame_id"
                        + " LEFT JOIN  css_style cssStyle ON pseudoSelector.id = cssStyle.pseudo_selector_id"
                        + " LEFT JOIN  html_node cssStyleNode ON cssStyle.html_tag_id = cssStyleNode.id"
                        + " where mediaQuery.html_project_id  = :id " +
                        "OR node.project_id  = :id " +
                        "OR cssStyleNode.project_id  = :id " +
                        "OR keyFrame.html_project_id  = :id "
                ,
                "PseudoSelectorMapping" )
                ;
        namedQueryPseudoSelector.setParameter("id", projectId);
        List<PseudoSelector> arrayResultPseudoSelector =  namedQueryPseudoSelector.getResultList();

        Query namedQueryCssValue = this.entityManager.createNativeQuery(
                "SELECT " +

                " cssV.id cssV_id, cssV.css_identity cssV_css_identity, cssV.inset cssV_inset, cssV.special_val_gradient cssV_special_val_gradient, cssV.unit_name cssV_unit_name, cssV.unit_name_second cssV_unit_name_second, cssV.unit_name_third cssV_unit_name_third, cssV.unit_name_fourth cssV_unit_name_fourth, cssV.unit_name_fifth cssV_unit_name_fifth, cssV.unit_name_sixth cssV_unit_name_sixth, cssV.unit_name_seventh cssV_unit_name_seventh, cssV.unit_name_eighth cssV_unit_name_eighth, cssV.unit_name_ninth cssV_unit_name_ninth, cssV.value cssV_value, cssV.value_second cssV_value_second, cssV.value_third cssV_value_third, cssV.value_fourth cssV_value_fourth, cssV.value_fifth cssV_value_fifth, cssV.value_sixth cssV_value_sixth, cssV.value_seventh cssV_value_seventh, cssV.value_eighth cssV_value_eighth, cssV.value_ninth cssV_value_ninth, cssV.css_style_id cssV_css_style_id, cssV.media_query_id cssV_media_query_id " +
                        " from css_value cssV "
                        + " LEFT JOIN  media_query mediaQuery ON cssV.media_query_id = mediaQuery.id"
                        + " LEFT JOIN  css_style cssStyle ON cssV.css_style_id = cssStyle.id"
                        + " LEFT JOIN  html_node cssStyleNode ON cssStyle.html_tag_id = cssStyleNode.id"
                        + " LEFT JOIN  css_style cssChild ON cssV.css_style_id = cssChild.id"
                        + " LEFT JOIN  css_style cssParent ON cssChild.parent_id = cssParent.id"
                        + " LEFT JOIN  html_node cssStyleParentNode ON cssParent.html_tag_id = cssStyleParentNode.id"
                        + " LEFT JOIN  pseudo_selector pseudoSelectorParent ON cssParent.pseudo_selector_id = pseudoSelectorParent.id"
                        + " LEFT JOIN  key_frame keyFrameCssParent ON pseudoSelectorParent.key_frame_id = keyFrameCssParent.id"
                        + " LEFT JOIN  html_node nodePseudoSelCssParent ON nodePseudoSelCssParent.id = pseudoSelectorParent.html_tag_id"
                        + " LEFT JOIN  pseudo_selector pseudoSelector ON cssStyle.pseudo_selector_id = pseudoSelector.id"
                        + " LEFT JOIN  key_frame keyFrameCss ON pseudoSelector.key_frame_id = keyFrameCss.id"
                        + " LEFT JOIN  html_node nodePseudoSelCss ON nodePseudoSelCss.id = pseudoSelector.html_tag_id"
                        + " where mediaQuery.html_project_id  = :id " +
                        "OR cssStyleNode.project_id  = :id " +
                        "OR cssStyleParentNode.project_id  = :id " +
                        "OR keyFrameCssParent.html_project_id  = :id " +
                        "OR keyFrameCss.html_project_id  = :id " +
                        "OR nodePseudoSelCssParent.project_id  = :id " +
                        "OR nodePseudoSelCss.project_id  = :id "
                ,
                "CssValueMapping" )
                ;
        namedQueryCssValue.setParameter("id", projectId);
        List<CssValue> arrayResultCssValue =  namedQueryCssValue.getResultList();



        List<HtmlProject> htmlProjects = new ArrayList<>();

        Map<String, HtmlNode> tags = new HashMap<>();
        Map<Long, CssStyle> cssList = new HashMap<>();
        Map<Long, CssValue> cssValueList = new HashMap<>();
        Map<String, KeyFrame> keyFramesList = new HashMap<>();
        Map<Long, MediaQuery> mediaQueryList = new HashMap<>();
        Map<Long, FontFace> fontFaceList = new HashMap<>();
        Map<Long, AssetProject> assetList = new HashMap<>();
        Map<Long, PseudoSelector> pseudoSelectorList = new HashMap<>();

        arrayResultNodes.stream().forEach((record) -> {

            HtmlTag tag = (HtmlTag)record;
            if (tag.getDtype().equals("TextNode")) {
                TextNode textNode = new TextNode(tag.getId(), tag.getText());
                textNode.setParentId(tag.getParentId());
                textNode.setOrderNumber(tag.getOrderNumber());
                textNode.setShortUuid(tag.getShortUuid());

                tags.put(textNode.getId(), textNode);

            } else {
                tags.put(tag.getId(), tag);
            }

        });

        arrayResultCssStyle.stream().forEach((record) -> {
            cssList.put(record.getId(), record);
        });

        arrayResultFontFace.stream().forEach((record) -> {
            fontFaceList.put(record.getId(), record);
        });

        arrayResultKeyFrame.stream().forEach((record) -> {
            keyFramesList.put(record.getId(), record);
        });

        arrayResultAssetProject.stream().forEach((record) -> {
            assetList.put(record.getId(), record);
        });

        arrayResultMediaQuery.stream().forEach((record) -> {
            mediaQueryList.put(record.getId(), record);
        });

        arrayResultPseudoSelector.stream().forEach((record) -> {
            pseudoSelectorList.put(record.getId(), record);
        });

        arrayResultCssValue.stream().forEach((record) -> {
            cssValueList.put(record.getId(), record);
        });

        HtmlProject htmlProject = new HtmlProject(projectId);

        keyFramesList.entrySet().stream().forEach((record) -> {
            KeyFrame keyFrame = record.getValue();

            htmlProject.addKeyFrame(keyFrame);
        });

        mediaQueryList.entrySet().stream().forEach((record) -> {
            MediaQuery mediaQuery = record.getValue();

            htmlProject.addMediaQuery(mediaQuery);
        });

        fontFaceList.entrySet().stream().forEach((record) -> {
            FontFace fontFace = record.getValue();

            htmlProject.addFontFace(fontFace);
        });

        cssValueList.entrySet().stream().forEach((record) -> {
            CssValue cssVal = record.getValue();

            if (cssVal.getCssStyleId() != null) {
                CssStyle cssOwner = cssList.get(cssVal.getCssStyleId());
                cssOwner.addCssValue(cssVal);
            }

            if (cssVal.getMediaQueryId() != null) {
                MediaQuery mediaQuery = mediaQueryList.get(cssVal.getMediaQueryId());
                mediaQuery.addCssValue(cssVal);
            }
        });

        cssList.entrySet().stream().forEach((record) -> {
            CssStyle cssStyle = record.getValue();

            if (cssStyle.getHtmlTagId() != null) {
                HtmlTag cssOwner = (HtmlTag)tags.get(cssStyle.getHtmlTagId());
                cssOwner.addCssStyle(cssStyle);
            }

            if (cssStyle.getParentId() != null) {
                CssStyle cssOwner = cssList.get(cssStyle.getParentId());
                cssOwner.addChild(cssStyle);
            }

            if (cssStyle.getMediaQueryId() != null) {
                MediaQuery mediaQuery = mediaQueryList.get(cssStyle.getMediaQueryId());
                mediaQuery.addCssStyle(cssStyle);
            }

            if (cssStyle.getPseudoSelectorId() != null) {
                PseudoSelector pseudoSelector = pseudoSelectorList.get(cssStyle.getPseudoSelectorId());
                pseudoSelector.addCssStyle(cssStyle);
            }

        });

        pseudoSelectorList.entrySet().stream().forEach((record) -> {
            PseudoSelector pseudoSelector = record.getValue();

            if (pseudoSelector.getMediaQueryIdField() != null) {
                MediaQuery mediaQuery = mediaQueryList.get(pseudoSelector.getMediaQueryIdField());
                mediaQuery.addPseudoSelector(pseudoSelector);
            }

            if (pseudoSelector.getHtmlTagId() != null) {
                HtmlTag htmlTag = (HtmlTag)tags.get(pseudoSelector.getHtmlTagId());
                htmlTag.addPseudoSelector(pseudoSelector);
            }

            if (pseudoSelector.getKeyFrameId() != null) {
                KeyFrame keyFrame = keyFramesList.get(pseudoSelector.getKeyFrameId());
                keyFrame.addSelector(pseudoSelector);
            }
        });

        assetList.entrySet().stream().forEach((record) -> {
            AssetProject assetProject = record.getValue();

            if (assetProject.getFontFaceId() != null) {
                FontFace fontFace = fontFaceList.get(assetProject.getFontFaceId());
                fontFace.addSrc(assetProject);
            }

        });

        tags.entrySet().stream().forEach((record) -> {

            HtmlNode tag = record.getValue();

            if (tag.getParentId() != null) {
                HtmlTag parent = (HtmlTag)tags.get(tag.getParentId());
                parent.addChild(tag);
            } else {
                htmlProject.addItem(tag);
            }

        });

        return htmlProject;
    }

    private HtmlProject fetchNextFields(HtmlProject project, String sql)
    {
        Query namedQuerySecond = this.entityManager.createQuery( "SELECT distinct p from HtmlProject p "
               + sql

                        +  " where p = :project"
                , clazz);
        namedQuerySecond.setParameter("project", project);
        namedQuerySecond.setHint(QueryHints.PASS_DISTINCT_THROUGH, false);

        return (HtmlProject) namedQuerySecond.getSingleResult();
    }


}
