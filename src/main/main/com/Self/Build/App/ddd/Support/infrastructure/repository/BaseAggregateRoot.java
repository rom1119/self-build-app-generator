package com.Self.Build.App.ddd.Support.infrastructure.repository;

import com.Self.Build.App.ddd.Support.infrastructure.EventPublisher.DomainEventPublisher;
import com.Self.Build.App.ddd.Support.infrastructure.PropertyAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseAggregateRoot {
    public static enum AggregateStatus {
        ACTIVE, ARCHIVE
    }
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    @JsonView(PropertyAccess.Public.class)
    protected String id;

    @Version
    @JsonView(PropertyAccess.Public.class)
    protected Long version;

    @Enumerated(EnumType.ORDINAL)
    private AggregateStatus aggregateStatus = AggregateStatus.ACTIVE;

    @Transient
    @Autowired()
    protected DomainEventPublisher eventPublisher;

    @JsonIgnore()
    public boolean isRemoved() {
        return aggregateStatus == AggregateStatus.ARCHIVE;
    }

    protected void domainError(String message) {
//        throw new DomainOperationException(aggregateId, message);
    }

    public void markAsRemoved() {
        aggregateStatus = AggregateStatus.ARCHIVE;
    }
}
