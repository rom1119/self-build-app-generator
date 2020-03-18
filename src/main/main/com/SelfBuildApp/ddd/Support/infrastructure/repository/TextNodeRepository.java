package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.TextNode;
import org.springframework.stereotype.Repository;

@Repository
public class TextNodeRepository extends GenericJpaRepository<TextNode> {

}
