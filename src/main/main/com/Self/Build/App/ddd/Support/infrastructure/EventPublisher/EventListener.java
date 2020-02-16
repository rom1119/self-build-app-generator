package com.Self.Build.App.ddd.Support.infrastructure.EventPublisher;

public interface EventListener<T> {

    void handle(T event);
}
