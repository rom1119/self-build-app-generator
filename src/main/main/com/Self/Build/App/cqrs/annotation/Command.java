package com.Self.Build.App.cqrs.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Command {
    /**
     * Suggestion for a Server that this command may be run in asynchronous way.
     * <br>
     * If true than {@link CommandHandler} must return void - otherwise Serwer will throw an exception
     * @return
     */
    boolean asynchronous() default false;

    /**
     * Suggestion for a Server that this command should checked if the same command is sent again.<br>
     * If true than command class must implement equals and hashCode
     * @return
     */
    boolean unique() default false;

    /**
     * If unique is true than this property may specify maximum timeout in miliseconds before same command can be executed
     * @return
     */
    long uniqueStorageTimeout() default 0L;
}
