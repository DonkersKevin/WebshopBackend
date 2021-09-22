package com.DonkersK.Ecommerce.dto;

import lombok.Data;

@Data
public class PurchaseResponse {

    //Needs final for Lombok to generate constructor with @Data
    //@NonNull works too
    private final String orderTrackingNumber;
}
