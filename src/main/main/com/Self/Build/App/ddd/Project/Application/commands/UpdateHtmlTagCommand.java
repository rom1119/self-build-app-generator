package com.Self.Build.App.ddd.Project.Application.commands;

import com.Self.Build.App.cqrs.annotation.Command;
import com.Self.Build.App.ddd.CanonicalModel.AggregateId;
import com.Self.Build.App.ddd.Project.domain.HtmlTag;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class UpdateHtmlTagCommand implements Serializable {

    private HtmlTag tag;

    public UpdateHtmlTagCommand( HtmlTag tag) {
        this.tag = tag;
    }

    public HtmlTag getTag() {
        return tag;
    }

    public void setTag(HtmlTag tag) {
        this.tag = tag;
    }

}
