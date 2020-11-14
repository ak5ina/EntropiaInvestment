package com.scrippy.entropiainvestment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scrippy.entropiainvestment.Order;
import com.scrippy.entropiainvestment.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyOrderlistviewAdaptor extends ArrayAdapter<Order> {
    private  ArrayList<Order> orderList;
    private Context con;
    private double newBank;
    private String athenticationString;
    TextView tvStockamount, tvStockPrice, tvStockName, tvStockSellOrBuy, tvUserID, tvUserBank;


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_order_object, parent, false);
        }

         tvStockName = convertView.findViewById(R.id.my_order_stockname);
         tvStockPrice = convertView.findViewById(R.id.my_order_price);
         tvStockamount = convertView.findViewById(R.id.my_order_amount);
        tvStockSellOrBuy = convertView.findViewById(R.id.my_order_sell_or_buy);
        tvUserID = convertView.findViewById(R.id.my_order_user);
        tvUserBank = convertView.findViewById(R.id.my_order_user_bank);
        Button btn_cancel_order = convertView.findViewById(R.id.my_order_cancel);
        Button btn_confirm_order = convertView.findViewById(R.id.my_order_confirm);

        if (orderList.get(position).isThisABuyOrder()) {
            tvStockSellOrBuy.setText("Buy order");
        } else {
            tvStockSellOrBuy.setText("Sell order");
        }



        //Cancel BTN
        if (athenticationString != null) {


            tvStockName.setText("Stock: 1/100 " + orderList.get(position).getStockToBuyOrSell().getName() + " Stock");
            tvStockPrice.setText("Price: " + (orderList.get(position).getStockToBuyOrSell().getValue() * orderList.get(position).getAmountOfStockToBuy()));
            tvStockamount.setText("Amount: " + orderList.get(position).getAmountOfStockToBuy());
            btn_cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //        //Write a message to the database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users").child(athenticationString).child("myorders").child(orderList.get(position).getOrderID());
                    myRef.child("canceled").setValue(true);

//
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("users").child(athenticationString).child("intrestpayouts");
//
//                myRef.push().setValue(new IntrestPayout(5.3,"03/11/2020","Arkadia underground deed payout. 1 buck a deed", "TESTER"));

                }
            });
        } else {

            tvStockName.setText("Stock: 1/100 " + orderList.get(position).getStockToBuyOrSell1() + " Stock");
            tvStockPrice.setText("Price: " + (orderList.get(position).getTotalPrice()));
            tvStockamount.setText("Amount: " + orderList.get(position).getAmountOfStockToBuy());
            tvUserID.setText("User: "+ orderList.get(position).getUser().getAuthUID());
            tvUserBank.setText("User bank: "+ orderList.get(position).getUser().getBank());

            btn_confirm_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users").child(orderList.get(position).getUser().getAuthUID());

                    //BUY ORDER
                    if() {
                        newBank = orderList.get(position).getUser().getBank() - orderList.get(position).getTotalPrice();
                        for (int i = 0; i < orderList.get(position).getAmountOfStockToBuy()) {

                        }
                    }
                    else{
                        //SELL ORDER
                        newBank = orderList.get(position).getUser().getBank() + (orderList.get(position).getTotalPrice()*0.98);
                        for (int i = 0; i < orderList.get(position).getAmountOfStockToBuy()) {

                        }
                    }
                    myRef.child("bank").setValue(newBank);

                }
            });

        }

            return convertView;
    }

    public MyOrderlistviewAdaptor(@NonNull Context context, int resource, ArrayList<Order> orderList, String athenticationString) {
        super(context, resource, orderList);
        this.con = context;
        this.orderList = orderList;
        this.athenticationString = athenticationString;

    }

    public MyOrderlistviewAdaptor(@NonNull Context context, int resource, ArrayList<Order> orderList) {
        super(context, resource, orderList);
        this.con = context;
        this.orderList = orderList;
        this.athenticationString = null;

    }


}
