package com.orion.ordermanagement.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.orion.ordermanagement.R;
import com.orion.ordermanagement.interfaces.OnEditOrderListener;
import com.orion.ordermanagement.interfaces.OnResultCallBack;
import com.orion.ordermanagement.model.Order;
import com.orion.ordermanagement.util.Constants;
import com.orion.ordermanagement.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;

class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final Context context;
    private final OnResultCallBack callBack;
    private final OnEditOrderListener editOrderListener;
    private List<Order> orderList = new ArrayList<>();

    public OrderAdapter(Context context, OnResultCallBack callBack, OnEditOrderListener editOrderListener) {
        this.context = context;
        this.callBack = callBack;
        this.editOrderListener = editOrderListener;

    }

    public void setData(List<Order> orderList) {
        this.orderList.clear();
        this.orderList.addAll(orderList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.order_item, null);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i) {
        orderViewHolder.textView.setText(orderList.get(i).toString());

    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
        private Button editButton, deleteButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.order_name);
            editButton = itemView.findViewById(R.id.edit);
            deleteButton = itemView.findViewById(R.id.delete);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.edit) {
                //Handle Edit button click
                int adapterPosition = this.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    editOrderListener.onEditOrder(orderList.get(adapterPosition));
                }

            } else if (v.getId() == R.id.delete) {
                //Handle Delete button click
                int adapterPosition = this.getAdapterPosition();
                showDeleteDialog(adapterPosition);
            } else if (v.getId() == R.id.order_name) {
                Order order = orderList.get(this.getAdapterPosition());
                Intent intent = new Intent(context, OrderDetailsActivity.class);

                intent.putExtra(Constants.ORDER_DETAILS_KEY, order);
                context.startActivity(intent);

            }
        }
    }

    private void showDeleteDialog(final int adapterPosition) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("Do you want to delete this order ? ").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    FirebaseUtil.deleteOrder(context, orderList.get(adapterPosition), callBack);
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).create();

        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }

    }
}
