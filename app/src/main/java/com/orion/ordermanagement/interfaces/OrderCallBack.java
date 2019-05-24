package com.orion.ordermanagement.interfaces;

import com.orion.ordermanagement.model.Order;

import java.util.List;

public interface OrderCallBack {

    void onOrdersFetched(List<Order> orders);
}
