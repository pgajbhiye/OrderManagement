package com.orion.ordermanagement.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.ordermanagement.interfaces.OnResultCallBack;
import com.orion.ordermanagement.interfaces.OrderCallBack;
import com.orion.ordermanagement.model.Order;
import com.orion.ordermanagement.model.User;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtil {

    private static String LOG_TAG = FirebaseUtil.class.getName();

    /*
    *
    * {
      "orders" : {
        "-LfeWhwhN1ghHs3PG-Kk" : {   //orderid
          "aLong" : 33.7,
          "customerAddress" : "Hyderabad",
          "customerBuyerName" : "Xam",
          "customerPhoneNumber" : "988098",
          "lat" : 99.8,
          "orderDueDate" : "29-10-2019",
          "orderNum" : 1,
          "orderTotal" : 200,
          "userId" : "-LfeWg30K8H0sbu2Bc8p"
        },
        "-LfeWxea2Ig_8ku24MmJ" : {
          "aLong" : 33.7,
          "customerAddress" : "Hyderabad",
          "customerBuyerName" : "Xam",
          "customerPhoneNumber" : "988098",
          "lat" : 99.8,
          "orderDueDate" : "29-10-2019",
          "orderNum" : 1,
          "orderTotal" : 200,
          "userId" : "-LfeWg30K8H0sbu2Bc8p"
        }
      },
      "users" : {
        "-LfeWg30K8H0sbu2Bc8p" : { //userid
          "email" : "kxkkd",
          "hasOrders" : true
        }
      }
    }

    * */


    interface DB_KEYS {

        String USERS = "users";
        String ORDERS = "orders";
        String HAS_ORDERS = "hasOrders";
        String EMAIL = "email";
        String USER_ID = "userId";
        String ORDER_ID = "orderId";

    }

    public static void createUser(final Context context, final String userName) {
        checkIfUserExistsInDb(context, userName, new OnResultCallBack() {
            @Override
            public void onResult(boolean result) {
                if (!result) {
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(DB_KEYS.USERS);
                    String userId = dbRef.push().getKey();

                    User user = new User(userName);
                    dbRef.child(userId).setValue(user);

                    //Persist user id for future CRUD operations
                    Utils.persistString(context, Constants.USER_ID, userId);
                }
            }
        });
    }

    private static void checkIfUserExistsInDb(Context context, String userName, final OnResultCallBack callBack) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(DB_KEYS.USERS);

        ref.orderByChild(DB_KEYS.EMAIL).equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.exists()) {
                    Log.d(LOG_TAG, "Email exists in db already ??" + dataSnapshot.exists());
                }
                callBack.onResult(dataSnapshot != null && dataSnapshot.exists());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callBack.onResult(false);
            }

        });
    }


    public static void saveUser(User user) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(DB_KEYS.USERS);
        dbRef.setValue(user);
    }

    public static void saveOrder(Context context, Order order) {
        String userId = Utils.getPrefString(context, Constants.USER_ID);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(DB_KEYS.USERS + "/" + userId);
        dbRef.child(DB_KEYS.HAS_ORDERS).setValue(true);

        DatabaseReference dbRefOrder = FirebaseDatabase.getInstance().getReference(DB_KEYS.ORDERS);
        //Save Or Update
        String orderId = order.getOrderId() == null ? dbRef.push().getKey() : order.getOrderId();
        //order.setOrderId(orderId);
        dbRefOrder.child(orderId).setValue(order);
        dbRefOrder.child(orderId).child("userId").setValue(userId);
    }


    public static void getAllOrders(Context context, String userId, final OrderCallBack callback) {
        DatabaseReference dbRefOrder = FirebaseDatabase.getInstance().getReference(DB_KEYS.ORDERS);

        dbRefOrder.orderByChild(DB_KEYS.USER_ID).equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Order> orderList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    order.setOrderId(snapshot.getKey());
                    orderList.add(order);
                    Log.d(LOG_TAG, "Iterating over current user orders " + order);
                }
                callback.onOrdersFetched(orderList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onOrdersFetched(new ArrayList<Order>());

            }
        });

    }


    public static void deleteOrder(Context context, Order order, final OnResultCallBack callBack) {
        String userId = Utils.getPrefString(context, Constants.USER_ID);
        //TODO: check if we are deleting the last order for the user
        //and set the hasOrders = false

        DatabaseReference dbRefOrder = FirebaseDatabase.getInstance().getReference(DB_KEYS.ORDERS);

        dbRefOrder.child(order.getOrderId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Log.d(LOG_TAG, "Delete Operation " + (databaseError != null ? databaseError.getCode() : ""));
                if (databaseError == null) {
                    callBack.onResult(true);
                    return;
                }

                callBack.onResult(false);

            }
        });

    }

   /* public static void updateOrder(Order order) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(DB_KEYS.ORDERS);
        dbRefOrder.child(order.getOrderId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Log.d(LOG_TAG, "Delete Operation " + (databaseError != null ? databaseError.getCode() : ""));
                if (databaseError == null) {
                    callBack.onResult(true);
                    return;
                }

                callBack.onResult(false);

            }
        });

        dbRef.setValue(order);

    }*/

}
