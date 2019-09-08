package com.Self.Build.App.ddd.Support.infrastructure.repository;

import java.io.Serializable;

public interface DomainEventPublisher {
    void publish(Serializable event);

}
