package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendMediaQueryToHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendTagToHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Project.domain.HtmlNode;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Support.infrastructure.Generator.impl.HtmlNodeShortUUID;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlNodeRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.MediaQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class AppendMediaQueryToHtmlProjectHandler implements CommandHandler<AppendMediaQueryToHtmlProjectCommand, HtmlProject> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private MediaQueryRepository mediaQueryRepository;

    @Autowired
    private HtmlNodeRepository htmlNodeRepository;

    @Autowired
    private HtmlNodeShortUUID shortUUID;

    @Override
    @Transactional
    public HtmlProject handle(AppendMediaQueryToHtmlProjectCommand command) {
        HtmlProject htmlProject = projectRepository.load(command.getProjectId().getId());
        htmlProject.addMediaQuery(command.getMediaQuery());
        command.getMediaQuery().getCssValues().forEach((CssValue e) -> {
            e.setMediaQuery(command.getMediaQuery());
        });

        mediaQueryRepository.save(command.getMediaQuery());
        return htmlProject;
    }
}
