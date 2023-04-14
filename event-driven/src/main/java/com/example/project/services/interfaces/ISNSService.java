package com.example.project.services.interfaces;

import com.example.project.entities.Order;

public interface ISNSService {

    void subscribeEmail(String topicArn, String email);

    void sendNotification(Order order, String topicArn);

}
