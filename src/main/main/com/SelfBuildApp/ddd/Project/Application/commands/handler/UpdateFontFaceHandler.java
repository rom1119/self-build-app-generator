package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.CreateFontFaceCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateFontFaceCommand;
import com.SelfBuildApp.ddd.Project.domain.FontFace;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.FontFaceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

@CommandHandlerAnnotation
public class UpdateFontFaceHandler implements CommandHandler<UpdateFontFaceCommand, FontFace> {
    @Autowired
    private FontFaceRepository repository;

    @Override
    @Transactional
    public FontFace handle(UpdateFontFaceCommand command) {

        FontFace dto = command.getFontFace();
        FontFace dbEntity = repository.findById(dto.getId()).get();

        dbEntity.setName(dto.getName());
        dbEntity.setVersion(dto.getVersion());

        return command.getFontFace();
    }
}
