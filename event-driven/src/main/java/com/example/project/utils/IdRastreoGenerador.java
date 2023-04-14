package com.example.project.utils;

import java.util.UUID;

public class IdRastreoGenerador {

    public static String generateTrackingId(){
        UUID uuid = UUID.randomUUID();


        String randomUUIDString = uuid.toString();

        String IdRastreo = randomUUIDString.replaceAll("-", "").substring(0, 15);




        return IdRastreo.toUpperCase();
    }

}
