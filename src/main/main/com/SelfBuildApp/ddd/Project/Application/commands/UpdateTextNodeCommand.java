package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.TextNode;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class UpdateTextNodeCommand implements Serializable {

    private TextNode textNode;
    private AggregateId textNodeId;


    public UpdateTextNodeCommand(AggregateId textNodeId, TextNode tag) {
        this.textNode = tag;
        this.textNodeId = textNodeId;
    }

    public TextNode getTextNode() {
        return textNode;
    }

    public void setTextNode(TextNode textNode) {
        this.textNode = textNode;
    }

    public AggregateId getTextNodeId() {
        return textNodeId;
    }

    public void setTextNodeId(AggregateId textNodeId) {
        this.textNodeId = textNodeId;
    }
}
