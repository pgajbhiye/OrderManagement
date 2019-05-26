package com.orion.ordermanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orion.ordermanagement.R;
import com.orion.ordermanagement.model.Order;
import com.orion.ordermanagement.util.Constants;

public class OrderDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);

        }

        Intent intent = getIntent();
        Order orderDetails = intent.getParcelableExtra(Constants.ORDER_DETAILS_KEY);
        if (orderDetails != null) {

            LinearLayout zero = findViewById(R.id.zero);
            TextView label0 = zero.findViewById(R.id.label);
            TextView value0 = zero.findViewById(R.id.value);
            label0.setText(getString(R.string.order_id_label));
            zero.setVisibility(View.GONE);
            value0.setText(orderDetails.getOrderId());


            LinearLayout one = findViewById(R.id.one);
            TextView label1 = one.findViewById(R.id.label);
            TextView value1 = one.findViewById(R.id.value);
            label1.setText(getString(R.string.order_due_label));
            value1.setText(orderDetails.getOrderDueDate());

            LinearLayout two = findViewById(R.id.two);
            TextView label2 = two.findViewById(R.id.label);
            TextView value2 = two.findViewById(R.id.value);
            label2.setText(getString(R.string.order_customer_name_label));
            value2.setText(orderDetails.getCustomerBuyerName());


            LinearLayout three = findViewById(R.id.three);
            TextView label3 = three.findViewById(R.id.label);
            TextView value3 = three.findViewById(R.id.value);
            label3.setText(getString(R.string.order_customer_address_label));
            value3.setText(orderDetails.getCustomerAddress());


            LinearLayout four = findViewById(R.id.four);
            TextView label4 = four.findViewById(R.id.label);
            TextView value4 = four.findViewById(R.id.value);
            label4.setText(getString(R.string.order_customer_phone_label));
            value4.setText(orderDetails.getCustomerPhoneNumber());


            LinearLayout five = findViewById(R.id.five);
            TextView label5 = five.findViewById(R.id.label);
            TextView value5 = five.findViewById(R.id.value);
            label5.setText(getString(R.string.order_total_label));
            value5.setText(String.valueOf(orderDetails.getOrderTotal()));

            LinearLayout six = findViewById(R.id.six);
            TextView label6 = six.findViewById(R.id.label);
            TextView value6 = six.findViewById(R.id.value);
            label6.setText(getString(R.string.order_lat_label));
            value6.setText(String.valueOf(orderDetails.getLat()));

            LinearLayout seven = findViewById(R.id.seven);
            TextView label7 = seven.findViewById(R.id.label);
            TextView value7 = seven.findViewById(R.id.value);
            label7.setText(getString(R.string.order_lat_label));
            seven.setVisibility(View.GONE);
            value7.setText(String.valueOf(orderDetails.getLat()));


            LinearLayout eight = findViewById(R.id.eight);
            TextView label8 = eight.findViewById(R.id.label);
            TextView value8 = eight.findViewById(R.id.value);
            label8.setText(getString(R.string.order_long_label));
            value8.setText(String.valueOf(orderDetails.getaLong()));

            LinearLayout nine = findViewById(R.id.nine);
            TextView label9 = nine.findViewById(R.id.label);
            TextView value9 = nine.findViewById(R.id.value);
            label9.setText(getString(R.string.order_city_label));
            value9.setText(String.valueOf(TextUtils.isEmpty(orderDetails.getCity()) ? " - " : orderDetails.getCity()));

            LinearLayout ten = findViewById(R.id.ten);
            TextView label10 = ten.findViewById(R.id.label);
            TextView value10 = ten.findViewById(R.id.value);
            label10.setText(getString(R.string.order_country_label));
            value10.setText(String.valueOf(TextUtils.isEmpty(orderDetails.getCountry()) ? " - " : orderDetails.getCountry()));

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();

    }
}
