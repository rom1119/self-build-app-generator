package com.Self.Build.App.ddd.Support.infrastructure.EventPublisher;

import java.io.Serializable;

public interface DomainEventPublisher {
    void publish(Serializable event);

}
