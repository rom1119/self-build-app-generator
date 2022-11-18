package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.CreateHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class CreateHtmlProjectHandler implements CommandHandler<CreateHtmlProjectCommand, HtmlProject> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private HtmlProjectRepository repository;

    @Override
    public HtmlProject handle(CreateHtmlProjectCommand command) {
        HtmlProject htmlProject = new HtmlProject();

        htmlProject.setName(command.getProject().getName());
        htmlProject.setPageUrl(command.getProject().getPageUrl());
        repository.save(htmlProject);

        return htmlProject;
    }
}
