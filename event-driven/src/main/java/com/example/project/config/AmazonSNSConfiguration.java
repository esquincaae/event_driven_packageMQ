package com.example.project.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AmazonSNSConfiguration {

    private String SNS_ACCESS_KEY = "AKIA33XH47FEVXNO6HH3";

    private String SNS_SECRET_KEY = "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";

    @Primary
    @Bean
    public AmazonSNSClient amazonSNSClient() {
        return (AmazonSNSClient) AmazonSNSClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        SNS_ACCESS_KEY,
                                        SNS_SECRET_KEY
                                )
                        )
                )
                .build();
    }
}

