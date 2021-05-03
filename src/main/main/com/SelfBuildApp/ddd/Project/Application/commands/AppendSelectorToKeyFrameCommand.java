package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class AppendSelectorToKeyFrameCommand implements Serializable {

    private AggregateId parentTagId;
    private PseudoSelector pseudoSelector;

    public AppendSelectorToKeyFrameCommand(AggregateId parentTagId, PseudoSelector pseudoSelector) {
        this.parentTagId = parentTagId;
        this.pseudoSelector = pseudoSelector;
    }

    public AggregateId getParentTagId() {
        return parentTagId;
    }

    public void setParentTagId(AggregateId parentTagId) {
        this.parentTagId = parentTagId;
    }

    public PseudoSelector getPseudoSelector() {
        return pseudoSelector;
    }

    public void setPseudoSelector(PseudoSelector pseudoSelector) {
        this.pseudoSelector = pseudoSelector;
    }
}
