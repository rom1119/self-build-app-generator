package com.SelfBuildApp.ddd.Project.domain.project;

import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;


public interface CssProjectRepository {

    public HtmlProject load(AggregateId paymentId);

    public void save(HtmlProject payment);
}
