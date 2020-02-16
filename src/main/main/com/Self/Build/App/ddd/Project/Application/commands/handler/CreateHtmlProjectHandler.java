package com.Self.Build.App.ddd.Project.Application.commands.handler;

import com.Self.Build.App.cqrs.annotation.CommandHandlerAnnotation;
import com.Self.Build.App.cqrs.command.handler.CommandHandler;
import com.Self.Build.App.ddd.Project.Application.commands.CreateHtmlProjectCommand;
import com.Self.Build.App.ddd.Project.domain.HtmlProject;

@CommandHandlerAnnotation
public class CreateHtmlProjectHandler implements CommandHandler<CreateHtmlProjectCommand, HtmlProject> {
    @Override
    public HtmlProject handle(CreateHtmlProjectCommand command) {
        HtmlProject htmlProject = new HtmlProject();

        htmlProject.setName(command.getName());
        return htmlProject;
    }
}
