package com.example.project.controllers.dtos.requests;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter @Setter
public class CreateOrderRequest implements Serializable {
    @NotBlank
    @NotNull
    private String address;
    private String city;

    @NotBlank
    @NotNull
    private String state;

    private String postalCode;

    private String shipTo;

    private String associatedEmail;

    private CreatePackageRequest packageRequest;
}
