package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.MediaQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaQueryRepository extends JpaRepository<MediaQuery, Long> {

    @Query(value = "SELECT * FROM media_query m WHERE m.html_project_id = ?;",
            nativeQuery = true)
    public List<MediaQuery> findAllForProjectId(String projectId);



}
