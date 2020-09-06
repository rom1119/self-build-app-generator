package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.MediaQuery;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class AppendMediaQueryToHtmlProjectCommand implements Serializable {

    private AggregateId projectId;
    private MediaQuery mediaQuery;

    public AppendMediaQueryToHtmlProjectCommand(AggregateId projectId, MediaQuery mediaQuery) {
        this.projectId = projectId;
        this.mediaQuery = mediaQuery;
    }

    public MediaQuery getMediaQuery() {
        return mediaQuery;
    }

    public void setMediaQuery(MediaQuery mediaQuery) {
        this.mediaQuery = mediaQuery;
    }

    public AggregateId getProjectId() {
        return projectId;
    }

    public void setProjectId(AggregateId projectId) {
        this.projectId = projectId;
    }

}
