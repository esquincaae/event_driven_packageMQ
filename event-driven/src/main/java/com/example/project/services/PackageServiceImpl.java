package com.example.project.services;

import com.example.project.controllers.dtos.requests.CreatePackageRequest;
import com.example.project.entities.Package;
import com.example.project.services.interfaces.IPackageService;
import com.example.project.repositories.IPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageServiceImpl implements IPackageService {
    private final IPackageRepository repository;
    public PackageServiceImpl(IPackageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Package create(CreatePackageRequest request) {
        Package packageResponse = new Package();


        packageResponse.setHeight(request.getHeight());
        packageResponse.setWidth(request.getWidth());
        packageResponse.setWeight(request.getWeight());

        return repository.save(packageResponse);
    }
}
