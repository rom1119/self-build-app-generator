package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class UpdatePseudoSelectorCommand implements Serializable {

    private PseudoSelector pseudoSelector;
    private Long id;


    public UpdatePseudoSelectorCommand(Long tagId, PseudoSelector s) {
        this.pseudoSelector = s;
        this.id = tagId;
    }

    public PseudoSelector getPseudoSelector() {
        return pseudoSelector;
    }

    public void setPseudoSelector(PseudoSelector pseudoSelector) {
        this.pseudoSelector = pseudoSelector;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
