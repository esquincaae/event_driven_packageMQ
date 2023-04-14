package com.example.project.services.interfaces;

import com.example.project.controllers.dtos.requests.CreatePackageRequest;
import com.example.project.entities.Package;

public interface IPackageService {

    Package create(CreatePackageRequest request);

}
