package com.SelfBuildApp.ddd.Support.infrastructure.EventPublisher;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultDomainEventPublisher  implements DomainEventPublisher {

    protected List<EventListener> listeners;

    public DefaultDomainEventPublisher() {
        listeners = new ArrayList<>();
    }

    @Override
    public void publish(Serializable event) {

        for (EventListener listener: listeners) {
            listener.handle(event);
        }

    }
}
