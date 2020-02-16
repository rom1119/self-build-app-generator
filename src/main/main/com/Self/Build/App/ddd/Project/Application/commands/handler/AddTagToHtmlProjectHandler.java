package com.Self.Build.App.ddd.Project.Application.commands.handler;

import com.Self.Build.App.cqrs.annotation.CommandHandlerAnnotation;
import com.Self.Build.App.cqrs.command.handler.CommandHandler;
import com.Self.Build.App.ddd.Project.Application.commands.AddTagToHtmlProjectCommand;
import com.Self.Build.App.ddd.Project.Application.commands.CreateHtmlProjectCommand;
import com.Self.Build.App.ddd.Project.domain.CssStyle;
import com.Self.Build.App.ddd.Project.domain.HtmlProject;
import com.Self.Build.App.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.Self.Build.App.ddd.Support.infrastructure.repository.HtmlTagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class AddTagToHtmlProjectHandler implements CommandHandler<AddTagToHtmlProjectCommand, HtmlProject> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private HtmlTagRepository tagRepository;

    @Override
    @Transactional
    public HtmlProject handle(AddTagToHtmlProjectCommand command) {
        HtmlProject htmlProject = projectRepository.load(command.getProjectId().getId());
        htmlProject.addItem(command.getTag());
        tagRepository.save(command.getTag());
        command.getTag().getCssStyleList().forEach((CssStyle e) -> {
            e.setHtmlTag(command.getTag());
        });
        return htmlProject;
    }
}
