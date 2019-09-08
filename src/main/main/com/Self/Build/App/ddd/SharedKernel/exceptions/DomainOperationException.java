package com.Self.Build.App.ddd.SharedKernel.exceptions;

import com.Self.Build.App.ddd.CanonicalModel.AggregateId;

@SuppressWarnings("serial")
public class DomainOperationException extends RuntimeException {

    private AggregateId aggregateId;

    public DomainOperationException(AggregateId aggregateId, String message){
        super(message);
        this.aggregateId = aggregateId;
    }

    public AggregateId getAggregateId() {
        return aggregateId;
    }
}
