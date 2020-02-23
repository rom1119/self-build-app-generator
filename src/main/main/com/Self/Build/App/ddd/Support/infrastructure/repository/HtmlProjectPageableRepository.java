package com.Self.Build.App.ddd.Support.infrastructure.repository;

import com.Self.Build.App.ddd.Project.domain.CssStyle;
import com.Self.Build.App.ddd.Project.domain.HtmlProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HtmlProjectPageableRepository extends JpaRepository<HtmlProject, String> {

}
