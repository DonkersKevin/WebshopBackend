package com.DonkersK.Ecommerce.service;

import com.DonkersK.Ecommerce.dao.CustomerRepository;
import com.DonkersK.Ecommerce.dto.Purchase;
import com.DonkersK.Ecommerce.dto.PurchaseResponse;
import com.DonkersK.Ecommerce.model.Customer;
import com.DonkersK.Ecommerce.model.Order;
import com.DonkersK.Ecommerce.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        //retrieve the order info from dto
        Order order = purchase.getOrder();

        //generate tracking nr
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //populate order with OrderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        //populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        //populate customer with order
        Customer customer = purchase.getCustomer();

        //check for existing customer
        String theEmail = customer.getEmail();
        Customer customerFromDB = customerRepository.findByEmail(theEmail);

        if (customerFromDB != null) {
            customer = customerFromDB;

            customer.add(order);
        }

            //save to database
            customerRepository.save(customer);
            System.out.println(customer.toString());

            //return response
            return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // generate random UUID number (UUID version-4)

        return UUID.randomUUID().toString();

    }
}
