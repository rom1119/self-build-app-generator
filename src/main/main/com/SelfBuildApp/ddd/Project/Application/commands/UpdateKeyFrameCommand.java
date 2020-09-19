package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.KeyFrame;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class UpdateKeyFrameCommand implements Serializable {

    private KeyFrame keyFrame;
    private AggregateId keyFrameId;


    public UpdateKeyFrameCommand(AggregateId tagId, KeyFrame keyFrame) {
        this.keyFrame = keyFrame;
        this.keyFrameId = tagId;
    }

    public KeyFrame getKeyFrame() {
        return keyFrame;
    }

    public void setKeyFrame(KeyFrame keyFrame) {
        this.keyFrame = keyFrame;
    }

    public AggregateId getKeyFrameId() {
        return keyFrameId;
    }

    public void setKeyFrameId(AggregateId keyFrameId) {
        this.keyFrameId = keyFrameId;
    }
}
