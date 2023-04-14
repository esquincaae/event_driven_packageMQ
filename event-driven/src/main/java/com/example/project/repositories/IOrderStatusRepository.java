package com.example.project.repositories;

import com.example.project.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    OrderStatus findOneByName(String name);

    Optional<OrderStatus> findOrderStatusByName(String name);

}
