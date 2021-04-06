package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.Project.domain.AssetProject;
import com.SelfBuildApp.ddd.Project.domain.FontFace;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class UpdateAssetCommand implements Serializable {

    private AssetProject assetProject;

    public UpdateAssetCommand(AssetProject assetProject) {
        this.assetProject = assetProject;
    }

    public AssetProject getAssetProject() {
        return assetProject;
    }
}
