package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.Project.domain.AssetProject;
import com.SelfBuildApp.ddd.Project.domain.FontFace;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class CreateAssetCommand implements Serializable {

    private AssetProject assetProject;

    public CreateAssetCommand(AssetProject assetProject) {
        this.assetProject = assetProject;
    }

    public AssetProject getAssetProject() {
        return assetProject;
    }
}
