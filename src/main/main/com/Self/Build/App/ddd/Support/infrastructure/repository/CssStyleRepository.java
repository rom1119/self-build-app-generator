package com.Self.Build.App.ddd.Support.infrastructure.repository;

import com.Self.Build.App.ddd.Project.domain.CssStyle;
import com.Self.Build.App.infrastructure.User.Model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CssStyleRepository extends JpaRepository<CssStyle, Long> {

}
