package com.Self.Build.App.ddd.Project.Application.commands;

import com.Self.Build.App.cqrs.annotation.Command;
import com.Self.Build.App.ddd.CanonicalModel.AggregateId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
