package com.example.project.services.interfaces;

import com.example.project.controllers.dtos.requests.CreateEventRequest;
import com.example.project.controllers.dtos.requests.UpdateEventRequest;
import com.example.project.entities.Event;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface IEventService {

    Event create(CreateEventRequest request) throws IOException;

    Event create(UpdateEventRequest request, String trackingId);

}
