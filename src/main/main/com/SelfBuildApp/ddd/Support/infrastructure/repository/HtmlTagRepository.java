package com.SelfBuildApp.ddd.Support.infrastructure.repository;

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
}
