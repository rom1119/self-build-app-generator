package com.Self.Build.App.ddd.Support.infrastructure.repository;

import com.Self.Build.App.ddd.CanonicalModel.AggregateId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Propagation;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;

public abstract class GenericJpaRepository<A extends BaseAggregateRoot> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<A> clazz;

    @Autowired
    private AutowireCapableBeanFactory spring;

    @SuppressWarnings("unchecked")
    public GenericJpaRepository() {
        this.clazz = ((Class<A>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Transactional
    public A load(String id) {
        //lock to be sure when creating other objects based on values of this aggregate
        A aggregate = entityManager.find(clazz, id, LockModeType.OPTIMISTIC);

        if (aggregate == null)
            return null;
//            throw new RuntimeException("Aggregate " + clazz.getCanonicalName() + " id = " + id + " does not exist");

        if (aggregate.isRemoved())
            return null;
//            throw new RuntimeException("Aggragate + " + id + " is removed.");

        spring.autowireBean(aggregate);

        return aggregate;
    }


    @Transactional
    public void save(A aggregate) {
        if (entityManager.contains(aggregate)){
            //locking Aggregate Root logically protects whole aggregate
            entityManager.lock(aggregate, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        }
        else{
            entityManager.persist(aggregate);
        }
    }

    @Transactional
    public void delete(String id){
        A entity = load(id);
        //just flag
        entity.markAsRemoved();
    }
}
