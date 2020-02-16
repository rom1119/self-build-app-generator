package com.Self.Build.App.ddd.Support.infrastructure.repository;

import com.Self.Build.App.ddd.CanonicalModel.AggregateId;
import com.Self.Build.App.ddd.Project.domain.HtmlProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

@Repository
public class HtmlProjectRepository extends GenericJpaRepository<HtmlProject> {

}
