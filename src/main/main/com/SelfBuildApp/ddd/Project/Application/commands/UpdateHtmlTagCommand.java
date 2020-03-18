package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class UpdateHtmlTagCommand implements Serializable {

    private HtmlTag tag;
    private AggregateId tagId;


    public UpdateHtmlTagCommand(AggregateId tagId, HtmlTag tag) {
        this.tag = tag;
        this.tagId = tagId;
    }

    public HtmlTag getTag() {
        return tag;
    }

    public void setTag(HtmlTag tag) {
        this.tag = tag;
    }

    public AggregateId getTagId() {
        return tagId;
    }

    public void setTagId(AggregateId tagId) {
        this.tagId = tagId;
    }
}
