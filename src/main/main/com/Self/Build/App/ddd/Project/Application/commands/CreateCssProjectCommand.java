package com.Self.Build.App.ddd.Project.Application.commands;

import com.Self.Build.App.cqrs.annotation.Command;
import com.Self.Build.App.ddd.CanonicalModel.AggregateId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class CreateCssProjectCommand implements Serializable {

    private AggregateId projectId;


}
