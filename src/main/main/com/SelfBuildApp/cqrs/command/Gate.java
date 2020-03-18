package com.SelfBuildApp.cqrs.command;

public interface Gate {
    public abstract Object dispatch(Object command);

}
