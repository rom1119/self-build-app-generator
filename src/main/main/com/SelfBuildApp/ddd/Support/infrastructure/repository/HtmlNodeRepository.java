package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.HtmlNode;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.TextNode;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class HtmlNodeRepository extends GenericJpaRepository<HtmlNode> {

    public List<HtmlNode> findByShortUUID(String shortUUID, String projectId)
    {

        Query namedQuery = this.entityManager.createNativeQuery("SELECT * FROM html_node where short_uuid=? and project_id=?");
        namedQuery.setParameter(1, shortUUID);
        namedQuery.setParameter(2, projectId);

        return namedQuery.getResultList();
    }
}
