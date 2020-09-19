package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendKeyFrameToHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendTagToHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Support.infrastructure.Generator.impl.HtmlNodeShortUUID;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class AppendKeyFrameToHtmlProjectHandler implements CommandHandler<AppendKeyFrameToHtmlProjectCommand, HtmlProject> {

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
    public HtmlProject handle(AppendKeyFrameToHtmlProjectCommand command) {
        HtmlProject htmlProject = projectRepository.load(command.getProjectId().getId());

        htmlProject.addKeyFrame(command.getKeyFrame());
        command.getKeyFrame().getSelectorList().forEach((PseudoSelector e) -> {
            pseudoSelectorRepository.save(e);
            e.setKeyFrame(command.getKeyFrame());

            e.getCssStyleList().forEach((CssStyle cssStyle) -> {
                cssStyle.setPseudoSelector(e);
            });
        });
        keyFrameRepository.save(command.getKeyFrame());


        return htmlProject;
    }
}
