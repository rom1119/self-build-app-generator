package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendChildToTagCommand;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Support.infrastructure.Generator.impl.HtmlNodeShortUUID;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class AppendChildToTagHandler implements CommandHandler<AppendChildToTagCommand, HtmlTag> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private HtmlTagRepository tagRepository;

    @Autowired
    private HtmlNodeShortUUID shortUUID;

    @Override
    @Transactional
    public HtmlTag handle(AppendChildToTagCommand command) {
        HtmlTag parentTag = tagRepository.load(command.getParentTagId().getId());
        parentTag.appendChild(command.getTag());
        String generate = shortUUID.generateUnique(command.getTag().getProject().getId());
        command.getTag().setShortUuid(generate);
//        command.getTag().getChildren().forEach((HtmlTag e) -> {
//            tagRepository.save(e);
//            e.setParent(command.getTag());
//        });
        tagRepository.save(command.getTag());
        command.getTag().getCssStyleList().forEach((CssStyle e) -> {
            e.setHtmlTag(command.getTag());
        });
        return command.getTag();
    }
}
