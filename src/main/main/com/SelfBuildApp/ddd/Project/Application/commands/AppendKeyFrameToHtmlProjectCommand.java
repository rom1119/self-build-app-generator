package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.KeyFrame;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class AppendKeyFrameToHtmlProjectCommand implements Serializable {

    private AggregateId projectId;
    private KeyFrame keyFrame;

    public AppendKeyFrameToHtmlProjectCommand(AggregateId projectId, KeyFrame keyFrame) {
        this.projectId = projectId;
        this.keyFrame = keyFrame;
    }

    public KeyFrame getKeyFrame() {
        return keyFrame;
    }

    public void setKeyFrame(KeyFrame keyFrame) {
        this.keyFrame = keyFrame;
    }

    public AggregateId getProjectId() {
        return projectId;
    }

    public void setProjectId(AggregateId projectId) {
        this.projectId = projectId;
    }

}
