package com.example.project.services.interfaces;

import com.example.project.controllers.dtos.requests.CreatePackageRequest;
import com.example.project.entities.Order;
import com.example.project.controllers.dtos.requests.CreateOrderRequest;

import java.io.IOException;

public interface IOrderService {
    Order create(CreateOrderRequest orderRequest, CreatePackageRequest packageRequest) throws IOException;

    void updateStatusToInProgress(String trackingId);

    void updateStatusToDelivered(String trackingId);

    Order findOneAndEnsureExistsById(Long id);

    Order findByTrackingId(String trackingId);
}
