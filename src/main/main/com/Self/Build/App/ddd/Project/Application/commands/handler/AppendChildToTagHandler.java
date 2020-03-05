package com.Self.Build.App.ddd.Project.Application.commands.handler;

import com.Self.Build.App.cqrs.annotation.CommandHandlerAnnotation;
import com.Self.Build.App.cqrs.command.handler.CommandHandler;
import com.Self.Build.App.ddd.Project.Application.commands.AppendChildToTagCommand;
import com.Self.Build.App.ddd.Project.Application.commands.AppendTagToHtmlProjectCommand;
import com.Self.Build.App.ddd.Project.domain.CssStyle;
import com.Self.Build.App.ddd.Project.domain.HtmlProject;
import com.Self.Build.App.ddd.Project.domain.HtmlTag;
import com.Self.Build.App.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.Self.Build.App.ddd.Support.infrastructure.repository.HtmlTagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class AppendChildToTagHandler implements CommandHandler<AppendChildToTagCommand, HtmlTag> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private HtmlTagRepository tagRepository;

    @Override
    @Transactional
    public HtmlTag handle(AppendChildToTagCommand command) {
        HtmlTag parentTag = tagRepository.load(command.getParentTagId().getId());
        parentTag.appendChild(command.getTag());
//        command.getTag().getChildren().forEach((HtmlTag e) -> {
//            tagRepository.save(e);
//            e.setParent(command.getTag());
//        });
        tagRepository.save(command.getTag());
        command.getTag().getCssStyleList().forEach((CssStyle e) -> {
            e.setHtmlTag(command.getTag());
        });
        return parentTag;
    }
}
