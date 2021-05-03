package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.HtmlNode;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.KeyFrame;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class KeyFrameRepository extends GenericJpaRepository<KeyFrame> {


    public List<KeyFrame> findAllForProjectId( String projectId)
    {

        Query namedQuery = this.entityManager.createNativeQuery("SELECT * FROM key_frame where html_project_id=?");
        namedQuery.setParameter(1, projectId);

        return namedQuery.getResultList();
    }
}
