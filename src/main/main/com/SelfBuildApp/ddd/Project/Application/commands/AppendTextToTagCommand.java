package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.TextNode;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class AppendTextToTagCommand implements Serializable {

    private AggregateId parentTagId;
    private TextNode textNode;

    public AppendTextToTagCommand(AggregateId parentTagId, TextNode textNode) {
        this.parentTagId = parentTagId;
        this.textNode = textNode;
    }

    public AggregateId getParentTagId() {
        return parentTagId;
    }

    public void setParentTagId(AggregateId parentTagId) {
        this.parentTagId = parentTagId;
    }

    public TextNode getTextNode() {
        return textNode;
    }

    public void setTextNode(TextNode textNode) {
        this.textNode = textNode;
    }
}
