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

    public void deleteTags( String projectId)
    {

        Query namedQuery = this.entityManager.createNativeQuery("" +
                "DELETE from html_node WHERE project_id = ?");

        namedQuery.setParameter(1, projectId);

        namedQuery.executeUpdate();
    }

    public void clearTagParents( String projectId)
    {

        Query namedQueryQ = this.entityManager.createNativeQuery("" +
                "UPDATE html_node set parent_id = NULL WHERE project_id = ?;\n");

        namedQueryQ.setParameter(1, projectId);

        namedQueryQ.executeUpdate();
    }

    public void deleteTagCss( String projectId)
    {

        Query namedQuery = this.entityManager.createNativeQuery("" +
                "DELETE from css_style as ss WHERE ss.html_tag_id IN (\n" +
                "    \n" +
                "        select n.id from html_node n\n" +
                "        where n.project_id = ?\n" +
                "    \n" +
                ");");

        namedQuery.setParameter(1, projectId);

        namedQuery.executeUpdate();
    }

    public void deletePseudoSelectorCss( String projectId)
    {

        Query namedQuery = this.entityManager.createNativeQuery("" +
                "DELETE from css_style as ss WHERE ss.pseudo_selector_id IN (\n" +
                "    \n" +
                "        select ps.id from pseudo_selector ps \n" +
                "        JOIN  html_node n ON n.id  = ps.html_tag_id\n" +
                "        where n.project_id = ?\n" +
                "    \n" +
                ");\n");
        namedQuery.setParameter(1, projectId);


        namedQuery.executeUpdate();
    }

    public void deleteMediaQueryCss(String projectId)
    {

        Query namedQuery = this.entityManager.createNativeQuery("" +
                "DELETE from css_style as ss WHERE ss.media_query_id IN (\n" +
                "    \n" +
                "        select m.id from media_query m \n" +
                "        where m.html_project_id = ?\n" +
                "    \n" +
                ");\n");
        namedQuery.setParameter(1, projectId);


        namedQuery.executeUpdate();
    }

    public void deleteCssValues( String projectId)
    {

        Query namedQuery = this.entityManager.createNativeQuery("" +
                "DELETE from css_value as val WHERE val.css_style_id IN (\n" +
                "    \n" +
                "        select c.id from css_style c\n" +
                "        JOIN  html_node n ON n.id  = c.html_tag_id\n" +
                "        where n.project_id = ?\n" +
                "    \n" +
                ");");

        namedQuery.setParameter(1, projectId);

        namedQuery.executeUpdate();
    }
}
