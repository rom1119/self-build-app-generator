package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PseudoSelectorRepository extends JpaRepository<PseudoSelector, Long> {

    @Query(value = "SELECT * FROM pseudo_selector p" +
            " JOIN html_node ON p.html_tag_id=html_node.id" +
            " JOIN css_style css ON p.id=css.pseudo_selector_id" +
            " WHERE html_node.project_id = ? AND css.media_query_id IS NULL;",
            nativeQuery = true)
    public List<PseudoSelector> findAllForProjectId(String projectId);

    @Query(value = "SELECT * FROM pseudo_selector p" +
            " JOIN html_node ON p.html_tag_id=html_node.id" +
            " JOIN css_style css ON p.id=css.pseudo_selector_id" +
            " WHERE html_node.project_id = ? AND css.media_query_id IS NULL AND p.media_query_id IS NULL;",
            nativeQuery = true)
    public List<PseudoSelector> findAllForProjectIdWhereNotHaveMediaQuery(String projectId);

    @Query(value = "SELECT * FROM pseudo_selector p" +
            " JOIN html_node ON p.html_tag_id=html_node.id" +
            " JOIN css_style css ON p.id=css.pseudo_selector_id" +
            " WHERE html_node.project_id = ? AND css.media_query_id IS NULL AND p.media_query_id IS NOT NULL;",
            nativeQuery = true)
    public List<PseudoSelector> findAllForProjectIdWhereHaveMediaQuery(String projectId);
//
//    @Query(value = "SELECT * FROM css_style  WHERE css_identity = ? limit 1;",
//            nativeQuery = true)
//    public CssStyle findOneByCssIdentity(String cssID);

}
