package com.DonkersK.Ecommerce.service;

import com.DonkersK.Ecommerce.dto.Purchase;
import com.DonkersK.Ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
