package com.Self.Build.App.cqrs.command;

public interface Gate {
    public abstract Object dispatch(Object command);

}
