package com.example.project.services;

import com.example.project.entities.OrderStatus;
import com.example.project.services.interfaces.IOrderStatusService;
import com.example.project.repositories.IOrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusImpl implements IOrderStatusService {

    private final IOrderStatusRepository repository;

    public OrderStatusImpl(IOrderStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderStatus findOneByName(String name) {
        return repository.findOneByName(name);
    }

    @Override
    public OrderStatus findOneByNameOrCreate(String name) {
        return repository.findOrderStatusByName(name).orElseGet(() -> create(name));
    }

    @Override
    public OrderStatus create(String name) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setName(name);


        return repository.save(orderStatus);
    }
}
