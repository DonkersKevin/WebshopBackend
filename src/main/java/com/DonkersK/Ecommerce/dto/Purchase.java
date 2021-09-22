package com.DonkersK.Ecommerce.dto;

import com.DonkersK.Ecommerce.model.Address;
import com.DonkersK.Ecommerce.model.Customer;
import com.DonkersK.Ecommerce.model.Order;
import com.DonkersK.Ecommerce.model.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;

    private Address shippingAddress;

    private Address billingAddress;

    private Order order;

    private Set<OrderItem> orderItems;
}
