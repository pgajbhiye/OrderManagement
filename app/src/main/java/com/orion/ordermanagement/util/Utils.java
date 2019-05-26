package com.orion.ordermanagement.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.orion.ordermanagement.model.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String LOG_TAG = Utils.class.getName();
    /**
     * Be between 4 and 10 characters long
     * alpha numeric and special character @#$%!
     */
    public static Pattern PASSWORD__PATTERN = Pattern.compile("((?=.*[a-zA-Z@#$%!])(?=.*\\d).{4,10})");


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


    public static boolean isPasswordValid(String password) {
        return (!TextUtils.isEmpty(password) && PASSWORD__PATTERN.matcher(password).matches());
    }

    public static boolean isPhoneValid(String phone) {
        return (!TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches());
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


    public static void persistBoolean(Context context, String key, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences("order", Context.MODE_PRIVATE);
        prefs.edit().putBoolean(key, value).apply();
    }

    public static boolean getPrefBoolean(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("order", Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }


    public static boolean shouldRemember(Context context) {
        return !TextUtils.isEmpty(Utils.getPrefString(context, Constants.User_NAME)) && !TextUtils.isEmpty(Utils.getPrefString(context, Constants.USER_PASSWORD));
    }

    public static boolean isUserRegistered(Context context) {
        return getPrefBoolean(context, Constants.IS_REGISTERED_USER);
    }

    public static GeoPoint getGeoCode(Context context, String strAddress) {


        Geocoder coder = new Geocoder(context);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.size() == 0) {
                return null;
            }

            Address location = address.get(0);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                location.getCountryName();
                String cityName = location.getAddressLine(0);
                String stateName = location.getAddressLine(1);
                String countryName = location.getAddressLine(2);

                p1 = new GeoPoint((double) (latitude),
                        (double) (longitude), cityName, stateName, countryName);// * 1E6

                Log.d(LOG_TAG, "GeoCode lat lon " + latitude + "lon " + longitude);

            } else {
                Toast.makeText(context, "Street Address not valid ", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in geocoding  ", e);
        }
        return p1;

    }


    public static class GeoPoint {

        private final String cityName;
        private final String stateName;
        private final String countryName;
        private double lat;
        private double along;

        public GeoPoint(double lat, double along, String cityName, String stateName, String countryName) {
            this.lat = lat;
            this.along = along;
            this.cityName = cityName;
            this.stateName = stateName;
            this.countryName = countryName;

        }

        public double getLatitude() {
            return lat;
        }

        public double getAlong() {
            return along;
        }


        public String getCityName() {
            return cityName;
        }

        public String getStateName() {
            return stateName;
        }

        public String getCountryName() {
            return countryName;
        }
    }
}
