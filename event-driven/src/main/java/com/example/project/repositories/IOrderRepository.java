package com.example.project.repositories;

import com.example.project.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    boolean existsOrderByTrackingId(String trackingId);

    Order findOneByTrackingId(String trackingId);

}
