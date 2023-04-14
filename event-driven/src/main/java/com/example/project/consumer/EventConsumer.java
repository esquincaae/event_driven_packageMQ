package com.example.project.consumer;

import com.example.project.services.interfaces.IEventService;
import com.example.project.services.interfaces.IOrderService;
import com.example.project.controllers.dtos.requests.CreateEventRequest;
import com.example.project.controllers.dtos.requests.UpdateEventRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EventConsumer {

    private final IEventService eventService;
    private final IOrderService orderService;

    public EventConsumer(IEventService eventService, IOrderService orderService) {
        this.eventService = eventService;
        this.orderService = orderService;
    }

    @RabbitListener(queues = "received_order_queue")
    public void inProgressListener(CreateEventRequest request) throws IOException {

        eventService.create(request);
    }

    @RabbitListener(queues = "in_progress_queue")
    public void receivedOrderListener(UpdateEventRequest request) {
        eventService.create(request, request.getTrackingId());

        orderService.updateStatusToInProgress(request.getTrackingId());
    }

    @RabbitListener(queues = "finished_order_queue")
    public void finishedOrderListener(UpdateEventRequest request){
        eventService.create(request, request.getTrackingId());

        orderService.updateStatusToDelivered(request.getTrackingId());
    }
}
