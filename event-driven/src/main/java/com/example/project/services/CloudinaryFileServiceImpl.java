package com.example.project.services;

import com.cloudinary.Cloudinary;
import com.example.project.services.interfaces.IFileService;
import com.example.project.controllers.dtos.responses.BaseResponse;
import com.example.project.entities.Order;
import com.example.project.services.interfaces.IOrderService;
import com.example.project.utils.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryFileServiceImpl implements IFileService {

    private String cloudName = "dnhonymfo";


    private String apiKey = "573137488582464";

    private String apiSecret = "-A_8fPbwLNnut3FsO30xWlU3c8s";

    private final FileUtils fileUtils;

    private final Cloudinary cloudinary = new Cloudinary();

    private final IOrderService orderService;

    private final Map<String, String> cloudinaryConfig = new HashMap<>();

    public CloudinaryFileServiceImpl(FileUtils fileUtils, IOrderService orderService) {
        this.fileUtils = fileUtils;
        this.orderService = orderService;
    }

    @Override
    public BaseResponse upload(MultipartFile multipartFile, String idRastreo) {
        String url = "";

        try {
            File file = fileUtils.convertMultipartFileToFile(multipartFile);

            String fileName = fileUtils.generateFileName(multipartFile);

            url = uploadFile(fileName, file);

            Order order = orderService.findByTrackingId(idRastreo);

            order.setFileUrl(url);

            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> message = new HashMap<>();
        message.put("url", url);


        return BaseResponse.builder()
                .data(message)
                .message("Se subió la imagen")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public void delete(String imageUrl) {

        String filename = getFilenamefromUrl(imageUrl);
        String publicId = "proyecto/archivos/" + fileUtils.removeExtensionFromFilename(filename);

        try {
            Map response = cloudinary.uploader().destroy(publicId, cloudinaryConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getFilenamefromUrl(String filename) {
        return filename.substring(filename.lastIndexOf("/") + 1);
    }

    private String uploadFile(String fileName, File file) throws IOException {
        cloudinaryConfig.put("public_id", fileName);
        Map response = cloudinary.uploader().upload(file, cloudinaryConfig);

        return (String) response.get("url");
    }

    @PostConstruct
    private void initializeCloudinary() {
        cloudinaryConfig.put("cloud_name", cloudName);
        cloudinaryConfig.put("api_key", apiKey);
        cloudinaryConfig.put("api_secret", apiSecret);
        cloudinaryConfig.put("folder", "/proyecto/archivos/");
    }
}
