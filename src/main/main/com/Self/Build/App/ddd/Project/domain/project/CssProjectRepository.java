package com.Self.Build.App.ddd.Project.domain.project;

import com.Self.Build.App.ddd.CanonicalModel.AggregateId;
import com.Self.Build.App.ddd.Project.domain.CssProject;


public interface CssProjectRepository {

    public CssProject load(AggregateId paymentId);

    public void save(CssProject payment);
}
