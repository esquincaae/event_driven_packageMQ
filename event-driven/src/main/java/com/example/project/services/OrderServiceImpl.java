package com.example.project.services;

import com.example.project.services.interfaces.*;
import com.example.project.constants.OrderStatusesEnum;
import com.example.project.controllers.dtos.requests.CreateOrderRequest;
import com.example.project.controllers.dtos.requests.CreatePackageRequest;
import com.example.project.entities.Order;
import com.example.project.entities.OrderStatus;
import com.example.project.repositories.IOrderRepository;
import com.example.project.utils.IdRastreoGenerador;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository repository;
    private final IOrderStatusService orderStatusService;
    private final IPackageService packageService;
    private final ISNSService snsService;

    public OrderServiceImpl(IOrderRepository repository, IOrderStatusService orderStatusService, IPackageService packageService, ISNSService snsService) {
        this.repository = repository;
        this.orderStatusService = orderStatusService;
        this.packageService = packageService;
        this.snsService = snsService;
    }

    @Override
    public Order create(CreateOrderRequest orderRequest, CreatePackageRequest packageRequest) {
        Order order = from(orderRequest);


        order.setTrackingId(IdRastreoGenerador.generateTrackingId());
        order.setOrderPackage(packageService.create(packageRequest));
        order.setStatus(getReceivedStatus());

//        snsService.subscribeEmail("arn:aws:sns:us-east-1:815456385353:Banca", order.getClientEmail());
        return repository.save(order);
    }

    @Override
    public void updateStatusToInProgress(String trackingId) {
        Order order = setStatus(OrderStatusesEnum.IN_PROGRESS, trackingId);

//        snsService.sendNotification(order, "arn:aws:sns:us-east-1:815456385353:Banca");


        repository.save(order);
    }

    @Override
    public void updateStatusToDelivered(String trackingId) {
        Order order = setStatus(OrderStatusesEnum.DELIVERED, trackingId);



//        snsService.sendNotification(order, "arn:aws:sns:us-east-1:815456385353:Banca");

        repository.save(order);
    }

    @Override
    public Order findOneAndEnsureExistsById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public Order findByTrackingId(String trackingId) {
        return repository.findOneByTrackingId(trackingId);
    }

    private Order from(CreateOrderRequest request){
        Order order = new Order();

        order.setAddress(request.getAddress());
        order.setCity(request.getCity());
        order.setState(request.getState());
        order.setPostalCode(request.getPostalCode());
        order.setReceiver(request.getShipTo());
        order.setClientEmail(request.getAssociatedEmail());


        OrderStatus status = orderStatusService.findOneByName("RECEIVED");
        order.setStatus(status);

        return order;
    }

    private Order setStatus(OrderStatusesEnum orderStatusEnum, String trackingId){
        Order order = repository.findOneByTrackingId(trackingId);


        String newStatus = orderStatusEnum.getOrderStatusEnum();
        order.setStatus(orderStatusService.findOneByNameOrCreate(newStatus));

        return order;
    }

    private OrderStatus getReceivedStatus() {
        return orderStatusService.findOneByNameOrCreate(OrderStatusesEnum.RECEIVED.getOrderStatusEnum());
    }
}
