package com.Self.Build.App.ddd.Project.domain.project;

import com.Self.Build.App.ddd.CanonicalModel.AggregateId;
import com.Self.Build.App.ddd.Project.domain.HtmlProject;


public interface CssProjectRepository {

    public HtmlProject load(AggregateId paymentId);

    public void save(HtmlProject payment);
}
