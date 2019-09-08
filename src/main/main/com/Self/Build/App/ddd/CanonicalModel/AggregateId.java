package com.Self.Build.App.ddd.CanonicalModel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("serial")
@Embeddable
public class AggregateId implements Serializable {

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    protected String aggregateId;

    public AggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    protected AggregateId() {
    }

    public String getId() {
        return aggregateId;
    }

    @Override
    public int hashCode() {
        return aggregateId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AggregateId other = (AggregateId) obj;
        if (aggregateId == null) {
            if (other.aggregateId != null)
                return false;
        } else if (!aggregateId.equals(other.aggregateId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return aggregateId;
    }
}
