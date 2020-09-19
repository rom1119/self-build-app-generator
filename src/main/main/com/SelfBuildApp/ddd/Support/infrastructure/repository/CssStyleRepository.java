package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import java.util.List;

@Repository
public interface CssStyleRepository extends JpaRepository<CssStyle, Long> {

    @Query(value = "SELECT * FROM css_style " +
            " JOIN html_node as node ON css_style.html_tag_id=node.id" +
            " JOIN html_project ON node.project_id=html_project.id" +
            " WHERE node.project_id = ?;",
            nativeQuery = true)
    public List<CssStyle> findAllForProjectId(String projectId);

    @Query(value = "SELECT * FROM css_style  " +
            " JOIN html_node as node ON css_style.html_tag_id=node.id" +
            " JOIN html_project as project ON node.project_id=project.id" +
            " WHERE css_identity = ? limit 1;",
            nativeQuery = true)
    public CssStyle findOneByCssIdentity(String cssID);

}
