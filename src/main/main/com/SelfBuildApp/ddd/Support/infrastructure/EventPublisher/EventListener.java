package com.SelfBuildApp.ddd.Support.infrastructure.EventPublisher;

public interface EventListener<T> {

    void handle(T event);
}
