package com.SelfBuildApp.cqrs.command.handler;

import com.SelfBuildApp.cqrs.annotation.Command;


/**
 *
 *
 * @param <C> command
 * @param <R> result type - for asynchronous {@link Command}commands (asynchronous=true) should be {@link Void}
 */
public interface CommandHandler<C, R> {

    public R handle(C command);

}
