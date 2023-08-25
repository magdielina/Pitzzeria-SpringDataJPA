package com.platzi.pizza.persistence.repository;

import com.platzi.pizza.persistence.entity.OrderEntity;
import com.platzi.pizza.persistence.projection.OrderSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {

    List<OrderEntity> findAllByDateAfter(LocalDateTime date);

    List<OrderEntity> findAllByMethodIn(List<String> methods);

    @Query(value = "SELECT * FROM pizza_order WHERE customer_id = :id", nativeQuery = true)
    List<OrderEntity> findCustomerOrders(@Param("id") String customerId);
    
    @Query(value = "SELECT po.order_id                   AS orderId, " +
                    "         cu.name                    AS customerName, " +
                    "         po.date                    AS orderDate, " +
                    "         po.total                   AS totalOrder, " +
                    "         GROUP_CONCAT(pi.name)     AS pizzaNames " +
                    "FROM pizza_order po " +
                    "         INNER JOIN customer cu ON po.customer_id = cu.customer_id " +
                    "         INNER JOIN order_item oi ON po.order_id = oi.order_id " +
                    "         INNER JOIN pizza pi ON oi.pizza_id = pi.pizza_id " +
                    "WHERE po.order_id = :orderId " +
                    "GROUP BY po.order_id, " +
                    "         cu.name, " +
                    "         po.date, " +
                    "         po.total", nativeQuery = true)
    OrderSummary findSummary(@Param("orderId") int orderId);

    @Procedure(value = "take_random_pizza_order", outputParameterName = "order_taken")
    boolean saveRandomOrder(@Param("customer_id") String customerId, @Param("method") String method);

}
