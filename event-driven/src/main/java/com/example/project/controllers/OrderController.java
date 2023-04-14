package com.example.project.controllers;

import com.example.project.controllers.dtos.responses.BaseResponse;
import com.example.project.entities.Order;
import com.example.project.services.interfaces.IFileService;
import com.example.project.services.interfaces.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/order")
@CrossOrigin
public class OrderController {

    private final IOrderService service;

    public OrderController(IOrderService service) {
        this.service = service;
    }

    @GetMapping("/{trackingId}")
    public Order get(@PathVariable String trackingId){
        return service.findByTrackingId(trackingId);
    }

}
