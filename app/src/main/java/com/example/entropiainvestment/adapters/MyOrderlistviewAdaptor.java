package com.example.entropiainvestment.adapters;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.entropiainvestment.IntrestPayout;
import com.example.entropiainvestment.Order;
import com.example.entropiainvestment.R;
import com.example.entropiainvestment.Stock;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyOrderlistviewAdaptor extends ArrayAdapter<Order> {
    private  ArrayList<Order> orderList;
    private Context con;
    private String athenticationString;


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_order_object, parent, false);
        }

        TextView tvStockName = convertView.findViewById(R.id.my_order_stockname);
        TextView tvStockPrice = convertView.findViewById(R.id.my_order_price);
        TextView tvStockamount = convertView.findViewById(R.id.my_order_amount);
        TextView tvStockSellOrBuy = convertView.findViewById(R.id.my_order_sell_or_buy);
        Button btn_cancel_order = convertView.findViewById(R.id.my_order_cancel);

        if (orderList.get(position).isThisABuyOrder()) {
            tvStockSellOrBuy.setText("Buy order");
        } else {
            tvStockSellOrBuy.setText("Sell order");
        }

        tvStockName.setText("Stock: 1/100 " + orderList.get(position).getStockToBuyOrSell().getName() + " Stock");
        tvStockPrice.setText("Price: " + (orderList.get(position).getStockToBuyOrSell().getValue() * orderList.get(position).getAmountOfStockToBuy()));
        tvStockamount.setText("Amount: " + orderList.get(position).getAmountOfStockToBuy());


        //Cancel BTN
        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        //        //Write a message to the database
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("users").child(athenticationString).child("myorders").child(orderList.get(position).getOrderID());
                            myRef.child("canceled").setValue(true);


            }
        });
            return convertView;
    }

    public MyOrderlistviewAdaptor(@NonNull Context context, int resource, ArrayList<Order> orderList, String athenticationString) {
        super(context, resource, orderList);
        this.con = context;
        this.orderList = orderList;
        this.athenticationString = athenticationString;

    }


}
