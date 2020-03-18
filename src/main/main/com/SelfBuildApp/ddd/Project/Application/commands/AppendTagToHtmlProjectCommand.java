package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class AppendTagToHtmlProjectCommand implements Serializable {

    private AggregateId projectId;
    private HtmlTag tag;

    public AppendTagToHtmlProjectCommand(AggregateId projectId, HtmlTag tag) {
        this.projectId = projectId;
        this.tag = tag;
    }

    public HtmlTag getTag() {
        return tag;
    }

    public void setTag(HtmlTag tag) {
        this.tag = tag;
    }

    public AggregateId getProjectId() {
        return projectId;
    }

    public void setProjectId(AggregateId projectId) {
        this.projectId = projectId;
    }

}
