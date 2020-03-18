package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendTagToHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.HtmlNode;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlNodeRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.TextNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class AppendTagToHtmlProjectHandler implements CommandHandler<AppendTagToHtmlProjectCommand, HtmlProject> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private HtmlTagRepository tagRepository;

    @Autowired
    private HtmlNodeRepository htmlNodeRepository;

    @Override
    @Transactional
    public HtmlProject handle(AppendTagToHtmlProjectCommand command) {
        HtmlProject htmlProject = projectRepository.load(command.getProjectId().getId());
        htmlProject.appendChild(command.getTag());
        command.getTag().getChildren().forEach((HtmlNode e) -> {
            htmlNodeRepository.save(e);
            e.setParent(command.getTag());
        });
        tagRepository.save(command.getTag());
        command.getTag().getCssStyleList().forEach((CssStyle e) -> {
            e.setHtmlTag(command.getTag());
        });
        return htmlProject;
    }
}
