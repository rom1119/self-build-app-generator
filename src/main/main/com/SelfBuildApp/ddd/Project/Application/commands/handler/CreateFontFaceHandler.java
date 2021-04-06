package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.CreateFontFaceCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.CreateHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.domain.FontFace;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.FontFaceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class CreateFontFaceHandler implements CommandHandler<CreateFontFaceCommand, FontFace> {
    @Autowired
    private FontFaceRepository repository;

    @Override
    @Transactional
    public FontFace handle(CreateFontFaceCommand command) {

        repository.save(command.getFontFace());
        return command.getFontFace();
    }
}
