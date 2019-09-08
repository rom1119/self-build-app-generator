package com.Self.Build.App.ddd.Project.Application.commands.handler;

import com.Self.Build.App.cqrs.annotation.CommandHandlerAnnotation;
import com.Self.Build.App.cqrs.command.handler.CommandHandler;
import com.Self.Build.App.ddd.Project.Application.commands.CreateCssProjectCommand;
import com.Self.Build.App.ddd.Project.domain.CssProject;

@CommandHandlerAnnotation
public class CreateCssProjectHandler implements CommandHandler<CreateCssProjectCommand, CssProject> {
    @Override
    public CssProject handle(CreateCssProjectCommand command) {
        return null;
    }
}
