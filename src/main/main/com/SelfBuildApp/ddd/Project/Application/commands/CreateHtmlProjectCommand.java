package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class CreateHtmlProjectCommand implements Serializable {

    private AggregateId projectId;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
