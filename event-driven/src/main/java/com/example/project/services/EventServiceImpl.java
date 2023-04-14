package com.example.project.services;

import com.example.project.entities.Event;
import com.example.project.entities.Order;
import com.example.project.services.interfaces.IEventService;
import com.example.project.controllers.dtos.requests.CreateEventRequest;
import com.example.project.controllers.dtos.requests.UpdateEventRequest;
import com.example.project.repositories.IEventRepository;
import com.example.project.services.interfaces.IOrderService;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class EventServiceImpl implements IEventService {

    private final IEventRepository repository;

    private final IOrderService orderService;

    public EventServiceImpl(IEventRepository repository, IOrderService orderService) {
        this.repository = repository;
        this.orderService = orderService;
    }

    @Override
    @Transactional
    public Event create(CreateEventRequest request) throws IOException {
        Order order = orderService.create(request.getOrder(), request.getOrder().getPackageRequest());

        Event event = from(request, order);
        return repository.save(event);
    }

    @Override
    public Event create(UpdateEventRequest request, String trackingId) {
        Order order = orderService.findByTrackingId(trackingId);

        Event event = new Event();
        event.setType(request.getType());
        event.setOrder(order);
        event.setDate(new Date(System.currentTimeMillis()));

        return repository.save(event);
    }

    private Event from(CreateEventRequest request, Order order){
        Event event = new Event();

        event.setType(request.getType());
        event.setOrder(order);
        event.setDate(new Date(System.currentTimeMillis()));

        return event;
    }
}
