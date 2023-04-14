package com.example.project.services.interfaces;

import com.example.project.controllers.dtos.responses.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    BaseResponse upload(MultipartFile multipartFile, String idRastreo);

    void delete(String imageUrl);

}
