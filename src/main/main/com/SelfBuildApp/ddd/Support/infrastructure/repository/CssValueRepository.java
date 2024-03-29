package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CssValueRepository extends JpaRepository<CssValue, Long> {

//    @Query(value = "SELECT * FROM css_style JOIN html_node ON css_style.html_tag_id=html_node.id WHERE html_node.project_id = ?;",
//            nativeQuery = true)
//    public List<CssStyle> findAllForProjectId(String projectId);

    @Query(value = "SELECT * FROM css_value  WHERE css_identity = ? limit 1;",
            nativeQuery = true)
    public CssStyle findOneByCssIdentity(String cssID);

}
