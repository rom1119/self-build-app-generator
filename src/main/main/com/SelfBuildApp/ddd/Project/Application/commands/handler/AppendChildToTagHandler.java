package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendChildToTagCommand;
import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Support.infrastructure.Generator.impl.HtmlNodeShortUUID;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlNodeRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.TextNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class AppendChildToTagHandler implements CommandHandler<AppendChildToTagCommand, HtmlTag> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private HtmlTagRepository tagRepository;

    @Autowired
    private TextNodeRepository textRepository;

    @Autowired
    private HtmlNodeRepository htmlNodeRepository;

    @Autowired
    private HtmlNodeShortUUID shortUUID;

    @Override
    @Transactional
    public HtmlTag handle(AppendChildToTagCommand command) {
        HtmlTag parentTag = tagRepository.load(command.getParentTagId().getId());
        appendRecursive(parentTag.getProject(), command.getTag(), parentTag);

        return command.getTag();
    }

    protected void appendRecursive(HtmlProject htmlProject, HtmlNode tagDto, HtmlTag parentTag)
    {
        String generate = shortUUID.generateUnique(htmlProject.getId());
        tagDto.setShortUuid(generate);
        htmlProject.appendChild(tagDto);
        if (parentTag != null) {
            tagDto.setParent(parentTag);
        }
        if (tagDto instanceof HtmlTag) {
            tagRepository.save(((HtmlTag)tagDto));
            ((HtmlTag)tagDto).getChildren().forEach((HtmlNode e) -> {
                htmlNodeRepository.save(e);
                e.setParent(tagDto);
            });
            ((HtmlTag)tagDto).getCssStyleList().forEach((CssStyle e) -> {
                e.setHtmlTag(((HtmlTag)tagDto));
            });

            for (HtmlNode child : ((HtmlTag)tagDto).getChildren()) {
                appendRecursive(htmlProject, child, ((HtmlTag)tagDto));
            }
            ((HtmlTag)tagDto).updateOrderChildren();

        } else {
            textRepository.save((TextNode) tagDto);

        }


    }
}
