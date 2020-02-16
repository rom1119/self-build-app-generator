package com.Self.Build.App.ddd.Project.Application.commands.handler;

import com.Self.Build.App.cqrs.annotation.CommandHandlerAnnotation;
import com.Self.Build.App.cqrs.command.handler.CommandHandler;
import com.Self.Build.App.ddd.Project.Application.commands.AddTagToHtmlProjectCommand;
import com.Self.Build.App.ddd.Project.Application.commands.UpdateHtmlTagCommand;
import com.Self.Build.App.ddd.Project.domain.CssStyle;
import com.Self.Build.App.ddd.Project.domain.HtmlProject;
import com.Self.Build.App.ddd.Project.domain.HtmlTag;
import com.Self.Build.App.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.Self.Build.App.ddd.Support.infrastructure.repository.HtmlTagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class UpdateHtmlTagHandler implements CommandHandler<UpdateHtmlTagCommand, HtmlTag> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private HtmlTagRepository tagRepository;

    @Override
    @Transactional
    public HtmlTag handle(UpdateHtmlTagCommand command) {
        tagRepository.save(command.getTag());
        return command.getTag();
    }
}
