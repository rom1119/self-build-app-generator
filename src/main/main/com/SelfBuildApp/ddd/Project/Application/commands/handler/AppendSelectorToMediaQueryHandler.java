package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendSelectorToMediaQueryCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendSelectorToTagCommand;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.MediaQuery;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;
import com.SelfBuildApp.ddd.Support.infrastructure.Generator.impl.HtmlNodeShortUUID;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.MediaQueryRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.PseudoSelectorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class AppendSelectorToMediaQueryHandler implements CommandHandler<AppendSelectorToMediaQueryCommand, PseudoSelector> {



    @Autowired
    private MediaQueryRepository repository;

    @Autowired
    private HtmlTagRepository tagRepository;

    @Autowired
    private PseudoSelectorRepository pseudoSelectorRepository;

    @Autowired
    private HtmlNodeShortUUID shortUUID;

    @Override
    @Transactional
    public PseudoSelector handle(AppendSelectorToMediaQueryCommand command) {
        MediaQuery mediaQuery = command.getMediaQuery();
        mediaQuery.addPseudoSelector(command.getPseudoSelector());

        HtmlTag parentTag = tagRepository.load(command.getParentTagId().getId());
        parentTag.addPseudoSelector(command.getPseudoSelector());

        pseudoSelectorRepository.save(command.getPseudoSelector());
        command.getPseudoSelector().getCssStyleList().forEach((CssStyle e) -> {
            e.setPseudoSelector(command.getPseudoSelector());
        });
        return command.getPseudoSelector();
    }
}
