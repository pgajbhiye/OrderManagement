package com.orion.ordermanagement.ui;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.orion.ordermanagement.R;
import com.orion.ordermanagement.interfaces.OnEditOrderListener;
import com.orion.ordermanagement.interfaces.OnResultCallBack;
import com.orion.ordermanagement.interfaces.OrderCallBack;
import com.orion.ordermanagement.model.Order;
import com.orion.ordermanagement.util.Constants;
import com.orion.ordermanagement.util.FirebaseUtil;
import com.orion.ordermanagement.util.Utils;

import java.util.List;

public class OrderActivity extends AppCompatActivity implements OrderCallBack, OnResultCallBack, OnEditOrderListener {


    private static final String LOG_TAG = OrderActivity.class.getName();
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_container);

        //Use this id is Firebase db generated inside json for unique user
        userId = Utils.getPrefString(this, Constants.USER_ID);

        recyclerView = findViewById(R.id.orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUtil.getAllOrders(this, userId, this);
        orderAdapter = new OrderAdapter(this, this, this);
        //orderAdapter.setData(new ArrayList<Order>());
        recyclerView.setAdapter(orderAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.new_order) {
            //create a new order list
            if (!isFinishing()) {
                handleNewOderDialog(null);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleNewOderDialog(final Order order) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.order_dialog, null);

        final EditText orderNumEt = view.findViewById(R.id.orderNum);
        final EditText orderDateEt = view.findViewById(R.id.orderDate);
        final EditText customerBuyerNameEt = view.findViewById(R.id.customerBuyerName);
        final EditText customerAddressEt = view.findViewById(R.id.customerAddress);
        final EditText customerPhoneNumberEt = view.findViewById(R.id.customerPhoneNumber);
        final EditText orderTotalEt = view.findViewById(R.id.orderTotal);

        if (order != null) {
            orderDateEt.setText(order.getOrderDueDate());
            customerBuyerNameEt.setText(order.getCustomerBuyerName());
            customerAddressEt.setText(order.getCustomerAddress());
            customerPhoneNumberEt.setText(order.getCustomerPhoneNumber());
            orderTotalEt.setText(String.valueOf(order.getOrderTotal()));
        } else {
            orderNumEt.setText("");
            orderDateEt.setText("");
            customerBuyerNameEt.setText("");
            customerAddressEt.setText("");
            customerPhoneNumberEt.setText("");
            orderTotalEt.setText("");
        }


        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int orderNum = 0;
                        String orderDate = "";
                        String customerBuyerName;
                        String customerAddress;
                        String customerPhoneNumber;
                        int orderTotal = 0;

                        if (!TextUtils.isEmpty(getValue(orderNumEt))) {
                            orderNum = Integer.parseInt(getValue(orderNumEt));
                        }

                        orderDate = getValue(orderDateEt);
                        customerBuyerName = getValue(customerBuyerNameEt);
                        customerAddress = getValue(customerAddressEt);
                        customerPhoneNumber = getValue(customerPhoneNumberEt);

                        if (!TextUtils.isEmpty(getValue(orderTotalEt))) {
                            orderTotal = Integer.parseInt(getValue(orderTotalEt));
                        }


                        //Creating new order. Edit Mode is off
                        Order newOrder = new Order(orderDate, customerBuyerName, customerAddress, customerPhoneNumber, orderTotal, 0.1, 0.1); //TODO: add support for lat along later
                        if (newOrder.isValidOrder()) {
                            //Edit Mode is On when passed order!=null. And update the orderid on done btn click
                            if (order != null) {
                                newOrder.setOrderId(order.getOrderId());
                            }


                            FirebaseUtil.saveOrder(OrderActivity.this, newOrder);
                            Log.d(LOG_TAG, "Retrieving the orders: after new order " + customerBuyerName);
                            FirebaseUtil.getAllOrders(OrderActivity.this, userId, OrderActivity.this);
                        } else {
                            Toast.makeText(OrderActivity.this, "Fill all order details", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .create();

        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

    private String getValue(EditText editText) {
        if (editText.getText() != null) {
            return editText.getText().toString();
        }
        return "";
    }


    @Override
    public void onOrdersFetched(List<Order> orders) {
        if (!orders.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            orderAdapter.setData(orders);

        } else {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(this, "No Orders found for this user ", Toast.LENGTH_SHORT).show();
        }
    }

    //Callback is invoked after "DELETE" Order
    @Override
    public void onResult(boolean result) {
        if (result) {
            //Fetch the orders from db and refresh the list
            FirebaseUtil.getAllOrders(OrderActivity.this, userId, OrderActivity.this);
        } else {
            Toast.makeText(this, "Delete Operation failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEditOrder(Order order) {
        handleNewOderDialog(order);

    }
}
