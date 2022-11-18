package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.HtmlNode;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class HtmlTagRepository extends GenericJpaRepository<HtmlTag> {


    public List<HtmlTag> findMainHtmlTagsForProject(String projectId)
    {

        Query namedQuery = this.entityManager.createNamedQuery("HtmlTag.findMainHtmlTagsForProject");
        namedQuery.setParameter(1, projectId);

        return namedQuery.getResultList();
    }

    public List<HtmlNode> findAllHtmlTagsForProject(String projectId)
    {

        Query namedQuery = this.entityManager.createNamedQuery("HtmlTag.findAllHtmlTagsForProject");
        namedQuery.setParameter(1, projectId);

        return namedQuery.getResultList();
    }
}
