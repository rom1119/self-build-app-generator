package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateAssetCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateFontFaceCommand;
import com.SelfBuildApp.ddd.Project.domain.AssetProject;
import com.SelfBuildApp.ddd.Project.domain.FontFace;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.AssetProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.FontFaceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class UpdateAssetHandler implements CommandHandler<UpdateAssetCommand, AssetProject> {
    @Autowired
    private AssetProjectRepository repository;

    @Autowired
    private PathFileManager pathFileManager;

    @Override
    @Transactional
    public AssetProject handle(UpdateAssetCommand command) {

        AssetProject dto = command.getAssetProject();
        AssetProject dbEntity = repository.findById(dto.getId()).get();

        dbEntity.setPathFileManager(pathFileManager);
        dbEntity.setType(dto.getType());
        dbEntity.setFormat(dto.getFormat());
        dbEntity.setResourceUrl(dto.getResourceUrl());
        if (dto.getFile() != null) {
            dbEntity.deleteResource();
            dbEntity.saveResource(dto.getFile());

        }

        return dto;
    }
}
