package com.SelfBuildApp.ddd.Support.infrastructure.repository;

import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HtmlProjectPageableRepository extends JpaRepository<HtmlProject, String> {

}
