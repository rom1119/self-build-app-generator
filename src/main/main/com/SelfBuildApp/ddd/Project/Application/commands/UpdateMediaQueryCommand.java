package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.MediaQuery;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class UpdateMediaQueryCommand implements Serializable {

    private MediaQuery mediaQuery;
    private AggregateId aggregateId;


    public UpdateMediaQueryCommand(AggregateId tagId, MediaQuery mediaQuery) {
        this.mediaQuery = mediaQuery;
        this.aggregateId = tagId;
    }

    public MediaQuery getMediaQuery() {
        return mediaQuery;
    }

    public void setMediaQuery(MediaQuery mediaQuery) {
        this.mediaQuery = mediaQuery;
    }

    public AggregateId getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }
}
