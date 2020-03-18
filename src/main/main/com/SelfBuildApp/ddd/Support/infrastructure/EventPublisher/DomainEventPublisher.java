package com.SelfBuildApp.ddd.Support.infrastructure.EventPublisher;

import java.io.Serializable;

public interface DomainEventPublisher {
    void publish(Serializable event);

}
