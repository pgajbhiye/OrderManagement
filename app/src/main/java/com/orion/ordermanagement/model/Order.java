package com.orion.ordermanagement.model;

import android.text.TextUtils;

public class Order {

    private String orderId; // equivalent to OrderNum
    private String orderDueDate;
    private String customerBuyerName;
    private String customerAddress;
    private String customerPhoneNumber;
    private int orderTotal;
    private double lat;
    private double aLong;

    public Order() {

    }

    public Order(/*String orderId, */String orderDueDate, String customerBuyerName, String customerAddress,
                 String customerPhoneNumber, int orderTotal, double lat, double aLong) {
        //this.orderId = orderId;
        this.orderDueDate = orderDueDate;
        this.customerBuyerName = customerBuyerName;
        this.customerAddress = customerAddress;
        this.customerPhoneNumber = customerPhoneNumber;
        this.orderTotal = orderTotal;
        this.lat = lat;
        this.aLong = aLong;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderDueDate() {
        return orderDueDate;
    }

    public String getCustomerBuyerName() {
        return customerBuyerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public double getLat() {
        return lat;
    }

    public double getaLong() {
        return aLong;
    }


    @Override
    public String toString() {
        return orderId + " " + orderDueDate + " " + customerBuyerName + " " + customerAddress + " " + customerPhoneNumber + " " + orderTotal + " " + lat + " " + aLong;
    }

    public boolean isValidOrder() {
        return (!TextUtils.isEmpty(orderDueDate) && !TextUtils.isEmpty(customerBuyerName) && !TextUtils.isEmpty(customerAddress) && !TextUtils.isEmpty(customerPhoneNumber) && orderTotal > 0);

    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
