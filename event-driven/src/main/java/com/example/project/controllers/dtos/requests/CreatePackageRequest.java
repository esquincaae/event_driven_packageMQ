package com.example.project.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter @Setter
public class CreatePackageRequest implements Serializable {


    @NotBlank
    @NotNull
    private Float weight;
    @NotBlank
    @NotNull
    private Float width;

    @NotBlank
    @NotNull
    private Float height;
}
