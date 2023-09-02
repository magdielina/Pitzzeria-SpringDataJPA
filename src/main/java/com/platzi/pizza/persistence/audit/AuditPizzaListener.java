package com.platzi.pizza.persistence.audit;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import jakarta.persistence.*;
import org.springframework.util.SerializationUtils;

public class AuditPizzaListener {
    private PizzaEntity currentValue;

    @PostLoad
    public void postLoad(PizzaEntity entity) {
//        System.out.println("POST LOAD");
//        this.currentValue = SerializationUtils.clone(entity);
    }

//    @PreUpdate
//    public void onPreUpdate(PizzaEntity entity) {
//        System.out.println("OLD VALUE: " + this.currentValue);
//    }
    @PostPersist
    @PostUpdate
    public void onPostPersist(PizzaEntity entity) {
        System.out.println("NEW VALUE: " + entity);
    }

    @PreRemove
    public void onPreDelete(PizzaEntity entity) {
        System.out.println(entity);
    }
}
