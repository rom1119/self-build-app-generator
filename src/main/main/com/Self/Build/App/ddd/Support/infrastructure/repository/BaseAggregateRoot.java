package com.Self.Build.App.ddd.Support.infrastructure.repository;

import com.Self.Build.App.ddd.CanonicalModel.AggregateId;
import com.Self.Build.App.ddd.SharedKernel.exceptions.DomainOperationException;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
@Component
@Scope("prototype")//created in domain factories, not in spring container, therefore we don't want eager creation
@MappedSuperclass
public abstract class BaseAggregateRoot {
    public static enum AggregateStatus {
        ACTIVE, ARCHIVE
    }
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "idValue", column = @Column(name = "id", nullable = false))})
    protected AggregateId aggregateId;


    @Version
    protected Long version;

    @Enumerated(EnumType.ORDINAL)
    private AggregateStatus aggregateStatus = AggregateStatus.ACTIVE;

    @Transient
    @Autowired
    protected DomainEventPublisher eventPublisher;

    public boolean isRemoved() {
        return aggregateStatus == AggregateStatus.ARCHIVE;
    }

    protected void domainError(String message) {
        throw new DomainOperationException(aggregateId, message);
    }

    public void markAsRemoved() {
        aggregateStatus = AggregateStatus.ARCHIVE;
    }
}
