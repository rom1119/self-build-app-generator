package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendChildToTagCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendTextToTagCommand;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.TextNode;
import com.SelfBuildApp.ddd.Support.infrastructure.Generator.impl.HtmlNodeShortUUID;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.TextNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class AppendTextToTagHandler implements CommandHandler<AppendTextToTagCommand, TextNode> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private HtmlTagRepository tagRepository;

    @Autowired
    private TextNodeRepository textRepository;

    @Autowired
    private HtmlNodeShortUUID shortUUID;

    @Override
    @Transactional
    public TextNode handle(AppendTextToTagCommand command) {
        HtmlTag parentTag = tagRepository.load(command.getParentTagId().getId());
        parentTag.appendChild(command.getTextNode());
        String generate = shortUUID.generateUnique(command.getTextNode().getProject().getId());
        command.getTextNode().setShortUuid(generate);

        textRepository.save(command.getTextNode());

        return command.getTextNode();
    }
}
