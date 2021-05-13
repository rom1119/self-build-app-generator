package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.CreateHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.domain.AssetProject;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.AssetProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

@CommandHandlerAnnotation
public class UpdateHtmlProjectHandler implements CommandHandler<UpdateHtmlProjectCommand, HtmlProject> {

    @Autowired
    private HtmlProjectRepository repository;


    @Override
    public HtmlProject handle(UpdateHtmlProjectCommand command) {

        HtmlProject dto = command.getProject();
        HtmlProject dbEntity = repository.load(dto.getId());
        dbEntity.setName(dto.getName());
        return dbEntity;
    }
}
