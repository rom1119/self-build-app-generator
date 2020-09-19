package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateHtmlTagCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateKeyFrameCommand;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.KeyFrame;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CommandHandlerAnnotation
public class UpdateKeyFrameHandler implements CommandHandler<UpdateKeyFrameCommand, KeyFrame> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private KeyFrameRepository keyFrameRepository;

    @Autowired
    private CssStyleRepository cssStyleRepository;

    @Autowired
    private CssValueRepository cssValueRepository;

    @Autowired
    private PathFileManager pathFileManager;

    @Override
    @Transactional
    public KeyFrame handle(UpdateKeyFrameCommand command) {
//        tagRepository.save(command.getTag());
        KeyFrame dto = command.getKeyFrame();
        KeyFrame DbENtity = keyFrameRepository.findById(Long.valueOf(command.getKeyFrameId().getId())).get();
        DbENtity.setPathFileManager(pathFileManager);


        DbENtity.setName(dto.getName());


        return DbENtity;
    }


}
