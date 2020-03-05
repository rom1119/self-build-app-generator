package com.Self.Build.App.ddd.Project.Application.commands;

import com.Self.Build.App.cqrs.annotation.Command;
import com.Self.Build.App.ddd.CanonicalModel.AggregateId;
import com.Self.Build.App.ddd.Project.domain.HtmlTag;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class AppendChildToTagCommand implements Serializable {

    private AggregateId parentTagId;
    private HtmlTag tag;

    public AppendChildToTagCommand(AggregateId parentTagId, HtmlTag tag) {
        this.parentTagId = parentTagId;
        this.tag = tag;
    }

    public AggregateId getParentTagId() {
        return parentTagId;
    }

    public void setParentTagId(AggregateId parentTagId) {
        this.parentTagId = parentTagId;
    }

    public HtmlTag getTag() {
        return tag;
    }

    public void setTag(HtmlTag tag) {
        this.tag = tag;
    }
}
