package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.AssetProject;
import com.SelfBuildApp.ddd.Project.domain.FontFace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetProjectRepository extends JpaRepository<AssetProject, Long> {


    @Query(value = "SELECT * FROM asset_project " +
            " JOIN html_project ON asset_project.project_id=html_project.id" +
            " WHERE asset_project.project_id = ? AND asset_project.font_face_id IS NULL ;",
            nativeQuery = true)
    public List<FontFace> findAllForProjectId(String projectId);

}
