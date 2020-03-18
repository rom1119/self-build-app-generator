package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.CreateHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;

@CommandHandlerAnnotation
public class CreateHtmlProjectHandler implements CommandHandler<CreateHtmlProjectCommand, HtmlProject> {
    @Override
    public HtmlProject handle(CreateHtmlProjectCommand command) {
        HtmlProject htmlProject = new HtmlProject();

        htmlProject.setName(command.getName());
        return htmlProject;
    }
}
