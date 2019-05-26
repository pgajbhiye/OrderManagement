package com.orion.ordermanagement.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class Order implements Parcelable {

    private String city;
    private String country;
    private String state;
    // equivalent to OrderNum
    private String orderId;
    private String orderDueDate;
    private String customerBuyerName;
    private String customerAddress;
    private String customerPhoneNumber;
    private int orderTotal;
    private double lat;
    private double aLong;


    //No-Arg constructor for Orders
    public Order() {

    }

    public Order(String orderDueDate, String customerBuyerName, String customerAddress,
                 String customerPhoneNumber, int orderTotal, double lat, double aLong, String city, String state, String country) {
        this.orderDueDate = orderDueDate;
        this.customerBuyerName = customerBuyerName;
        this.customerAddress = customerAddress;
        this.customerPhoneNumber = customerPhoneNumber;
        this.orderTotal = orderTotal;
        this.lat = lat;
        this.aLong = aLong;
        this.city = city;
        this.state = state;
        this.country = country;
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
        return "Order Id : " + orderId + "\nDue Date : " + orderDueDate + "\nCustomer Name : " + customerBuyerName + "\nCustomer Address : " + customerAddress;
    }

    public boolean isValidOrder() {
        return (!TextUtils.isEmpty(orderDueDate) && !TextUtils.isEmpty(customerBuyerName) && !TextUtils.isEmpty(customerAddress) && !TextUtils.isEmpty(customerPhoneNumber) && orderTotal > 0);

    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }


    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {


        @Override
        public Order createFromParcel(Parcel parcel) {
            return new Order(parcel);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[0];
        }
    };

    public Order(Parcel parcel) {
        orderDueDate = parcel.readString();
        customerBuyerName = parcel.readString();
        customerAddress = parcel.readString();
        customerPhoneNumber = parcel.readString();
        orderTotal = parcel.readInt();
        lat = parcel.readDouble();
        aLong = parcel.readDouble();
        city = parcel.readString();
        state = parcel.readString();
        country = parcel.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderDueDate);
        dest.writeString(this.customerBuyerName);
        dest.writeString(this.customerAddress);
        dest.writeString(this.customerPhoneNumber);
        dest.writeInt(this.orderTotal);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.aLong);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.country);
    }

}
