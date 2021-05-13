package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class UpdateHtmlProjectCommand implements Serializable {

    private HtmlProject Project;

    public UpdateHtmlProjectCommand(HtmlProject project) {
        Project = project;
    }

    public HtmlProject getProject() {
        return Project;
    }
}
