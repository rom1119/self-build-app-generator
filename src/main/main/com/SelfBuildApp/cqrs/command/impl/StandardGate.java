package com.SelfBuildApp.cqrs.command.impl;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.cqrs.command.Gate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StandardGate implements Gate {

    @Autowired
    private RunEnvironment runEnvironment;

    private GateHistory gateHistory = new GateHistory();

    /* (non-Javadoc)
     * @see pl.com.bottega.cqrs.command.impl.Gate#dispatch(java.lang.Object)
     */
    @Override
    public Object dispatch(Object command){
        if (! gateHistory.register(command)){
            //TODO log.info(duplicate)
            return null;//skip duplicate
        }

        if (isAsynchronous(command)){
            //TODO add to the queue. Queue should send this command to the RunEnvironment
            return null;
        }

        return runEnvironment.run(command);
    }

    /**
     * @param command
     * @return
     */
    private boolean isAsynchronous(Object command) {
        if (! command.getClass().isAnnotationPresent(Command.class))
            return false;

        Command commandAnnotation = command.getClass().getAnnotation(Command.class);
        return commandAnnotation.asynchronous();
    }
}
