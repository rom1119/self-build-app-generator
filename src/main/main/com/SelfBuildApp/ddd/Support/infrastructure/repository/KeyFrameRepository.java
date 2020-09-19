package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.KeyFrame;
import com.SelfBuildApp.ddd.Project.domain.MediaQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyFrameRepository extends JpaRepository<KeyFrame, Long> {

    @Query(value = "SELECT * FROM key_frame as a " +
            " JOIN css_value val ON node.project_id=html_project.id" +
            " JOIN html_node as node ON css_style.html_tag_id=node.id" +
            "WHERE a.project_id = ?;",
            nativeQuery = true)
    public List<KeyFrame> findAllForProjectId(String projectId);



}
