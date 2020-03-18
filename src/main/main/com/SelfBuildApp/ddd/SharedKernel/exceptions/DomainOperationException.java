package com.SelfBuildApp.ddd.SharedKernel.exceptions;

import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;

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
