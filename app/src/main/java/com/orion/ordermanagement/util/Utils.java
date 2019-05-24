package com.orion.ordermanagement.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Patterns;

import com.orion.ordermanagement.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {


  /*  public static List<Order> getData() {
        List<Order> orders = new ArrayList<>();
        //int orderNum, String orderDueDate, String customerBuyerName, String customerAddress,
        //                 String customerPhoneNumber, int orderTotal, long lat, long aLong
        Order order1 = new Order(1, "29-10-2019", "Kevin", "Hyderabad", "988098", 200, 99.8, 33.7);
        Order order2 = new Order(1, "29-10-2019", "Kevin", "Hyderabad", "988098", 200, 99.8, 33.7);
        Order order3 = new Order(1, "29-10-2019", "Kevin", "Hyderabad", "988098", 200, 99.8, 33.7);
        Order order4 = new Order(1, "29-10-2019", "Kevin", "Hyderabad", "988098", 200, 99.8, 33.7);
        Order order5 = new Order(1, "29-10-2019", "Kevin", "Hyderabad", "988098", 200, 99.8, 33.7);


        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);

        return orders;
    }*/


    public static boolean isNameValid(String name) {
        if (TextUtils.isEmpty(name) || TextUtils.isDigitsOnly(name)) return false;

        if (containsDigit(name)) return false;

        return true;
    }

    public static boolean containsDigit(String s) {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(s);

        return m.find();
    }

    public static boolean isEmailValid(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }


    public static boolean hasAllLoginFields(String userName, String password) {
        return (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password));

    }

    public static void persistString(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences("order", Context.MODE_PRIVATE);
        prefs.edit().putString(key, value).apply();
    }

    public static String getPrefString(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("order", Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static void removeStringKey(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("order", Context.MODE_PRIVATE);
        prefs.edit().remove(key).apply();
    }



    public static boolean shouldRemember(Context context) {
        return !TextUtils.isEmpty(Utils.getPrefString(context, Constants.User_NAME)) && !TextUtils.isEmpty(Utils.getPrefString(context, Constants.USER_PASSWORD));
    }

    public static Order createLocalOrder() {
        return null;
    }
}
