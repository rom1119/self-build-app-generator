package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.FontFace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FontFaceRepository extends JpaRepository<FontFace, Long> {


    @Query(value = "SELECT * FROM font_face " +
            " JOIN html_project ON font_face.project_id=html_project.id" +
            " WHERE font_face.project_id = ?;",
            nativeQuery = true)
    public List<FontFace> findAllForProjectId(String projectId);

}
