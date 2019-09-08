package com.Self.Build.App.cqrs.command.impl;

import com.Self.Build.App.cqrs.handler.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunEnvironment {
    public interface HandlersProvider{
        CommandHandler<Object, Object> getHandler(Object command);
    }

    @Autowired
    private HandlersProvider handlersProfiver;

    public Object run(Object command) {
        CommandHandler<Object, Object> handler = handlersProfiver.getHandler(command);

        //You can add Your own capabilities here: dependency injection, security, transaction management, logging, profiling, spying, storing commands, etc

        Object result = handler.handle(command);

        //You can add Your own capabilities here

        return result;
    }
}
