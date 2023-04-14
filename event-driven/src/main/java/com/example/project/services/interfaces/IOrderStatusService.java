package com.example.project.services.interfaces;

import com.example.project.entities.OrderStatus;

public interface IOrderStatusService {

    OrderStatus create(String name);

    OrderStatus findOneByName(String name);

    OrderStatus findOneByNameOrCreate(String name);

}
