package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendSelectorToKeyFrameCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendSelectorToTagCommand;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.KeyFrame;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;
import com.SelfBuildApp.ddd.Support.infrastructure.Generator.impl.HtmlNodeShortUUID;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.KeyFrameRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.PseudoSelectorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class AppendSelectorToKeyFrameHandler implements CommandHandler<AppendSelectorToKeyFrameCommand, PseudoSelector> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private KeyFrameRepository keyFrameRepository;

    @Autowired
    private PseudoSelectorRepository pseudoSelectorRepository;

    @Autowired
    private HtmlNodeShortUUID shortUUID;

    @Override
    @Transactional
    public PseudoSelector handle(AppendSelectorToKeyFrameCommand command) {
        KeyFrame parentTag = keyFrameRepository.load(command.getParentTagId().getId());
        parentTag.addSelector(command.getPseudoSelector());

        pseudoSelectorRepository.save(command.getPseudoSelector());
        command.getPseudoSelector().getCssStyleList().forEach((CssStyle e) -> {
            e.setPseudoSelector(command.getPseudoSelector());
        });
        return command.getPseudoSelector();
    }
}
