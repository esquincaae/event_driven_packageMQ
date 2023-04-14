package com.example.project.services;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.example.project.entities.Order;
import com.example.project.services.interfaces.ISNSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SNSServiceImpl implements ISNSService {
    private final AmazonSNSClient amazonSNSClient;

    public SNSServiceImpl(AmazonSNSClient amazonSNSClient) {
        this.amazonSNSClient = amazonSNSClient;
    }

    @Override
    public void subscribeEmail(String topicArn, String email) {
        String filterPolicy = "{\"clientEmail\":[\"equals\",\"" + email + "\"]}";

        SubscribeRequest request = new SubscribeRequest(topicArn, "email", email)
                .withProtocol("email")
                .withReturnSubscriptionArn(true)
                .withAttributes(Collections.singletonMap("FilterPolicy", filterPolicy));


        amazonSNSClient.subscribe(request);
    }

    @Override
    public void sendNotification(Order order, String topicArn) {
        String message = "";
        String orderStatus = order.getStatus().getName();

        switch (orderStatus){
            case "IN_PROGRESS":
                message = "Ya fue enviado tu paquete";
                break;

            case "DELIVERED":
                message = "Se le entrego tu paquete a " + order.getReceiver();
                break;


        }

        PublishRequest publishRequest = new PublishRequest().withTopicArn(topicArn).withMessage(message)
                .withMessageAttributes(generateEmailAttribute(order.getClientEmail()));
        amazonSNSClient.publish(publishRequest);
    }

    private Map<String, MessageAttributeValue> generateEmailAttribute(String email){
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("clientEmail", new MessageAttributeValue().withDataType("String").withStringValue(String.valueOf(email)));
        return messageAttributes;
    }

}
