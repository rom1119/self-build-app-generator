package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.CreateAssetCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.CreateFontFaceCommand;
import com.SelfBuildApp.ddd.Project.domain.AssetProject;
import com.SelfBuildApp.ddd.Project.domain.FontFace;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.AssetProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.FontFaceRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class CreateAssetHandler implements CommandHandler<CreateAssetCommand, AssetProject> {

    @Autowired
    private AssetProjectRepository repository;

    @Autowired
    private FontFaceRepository fontFaceRepository;

    @Autowired
    private HtmlProjectRepository htmlProjectRepository;

    @Autowired
    private PathFileManager pathFileManager;

    @Override
    @Transactional
    public AssetProject handle(CreateAssetCommand command) {

        HtmlProject htmlProject;
        FontFace fontFace;
        AssetProject dto = command.getAssetProject();
        if (dto.getProject().getId() != null) {
            htmlProject = htmlProjectRepository.load(dto.getProject().getId());
            dto.setProject(htmlProject);
        }

        if (dto.getFontFace().getId() != null) {
            fontFace = fontFaceRepository.findById(dto.getFontFace().getId()).get();
            dto.setFontFace(fontFace);
        }

        repository.save(dto);
        if (dto.getFile() != null) {
            dto.setPathFileManager(pathFileManager);
            dto.saveResource(dto.getFile());

        }
        return command.getAssetProject();
    }
}
