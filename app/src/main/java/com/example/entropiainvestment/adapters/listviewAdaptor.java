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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.entropiainvestment.Order;
import com.example.entropiainvestment.R;
import com.example.entropiainvestment.Stock;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class listviewAdaptor extends ArrayAdapter<Stock> {
    private  ArrayList<Stock> stockList;
    private Context con;
    private String athenticationString;
    public int orderAmountAsInteger;
    private double userbank;
    TextView orderTyper;


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_object, parent, false);
        }

        TextView tvStockName = convertView.findViewById(R.id.listview_object_stock_name);
        TextView tvStockPrice = convertView.findViewById(R.id.listview_object_price);
        TextView tvStockOwend = convertView.findViewById(R.id.listview_object_stock_owned);
        TextView tvStockCombinedValue = convertView.findViewById(R.id.listview_object_combined_value);
        final Button btn_buy = convertView.findViewById(R.id.listview_object_btn_buy);
        final Button btn_sell = convertView.findViewById(R.id.listview_object_btn_sell);

        tvStockName.setText("1/100 " + stockList.get(position).getName());
        tvStockPrice.setText("Price: " + stockList.get(position).getValue());
        tvStockOwend.setText("Amount owned: " + stockList.get(position).getAmountOwned());
        tvStockCombinedValue.setText("Value: " + stockList.get(position).getCombinedValueOfAllOwnedStock());

        //Buy BTN
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(parent.getContext());
                dialog.setContentView(R.layout.dialog);
                orderTyper = dialog.findViewById(R.id.order_buyorsell);
                TextView orderStockName = dialog.findViewById(R.id.order_stockname);
                TextView orderPrice = dialog.findViewById(R.id.order_price);
                EditText orderAmount = dialog.findViewById(R.id.order_amounttobuyorsell);
                orderAmount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if (s.length() > 0) {
                            orderAmountAsInteger = Integer.valueOf(s.toString());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                orderTyper.setText("Order: Buy");
                orderStockName.setText("Stock: " + stockList.get(position).getName());
                orderPrice.setText("Price: " + stockList.get(position).getValue());
                Button btn_proceed = dialog.findViewById(R.id.order_proceed);
                Button btn_cancel = dialog.findViewById(R.id.order_cancel);

                dialog.show();


                btn_proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //        //Write a message to the database
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("users").child(athenticationString);
                        Order currentOrder = new Order(stockList.get(position), true, orderAmountAsInteger, orderAmountAsInteger*stockList.get(position).getValue() );
                        if (currentOrder.getTotalPrice() <= userbank) {

                            if(currentOrder.getAmountOfStockToBuy() > 0) {
                                myRef.child("myorders").push().setValue(currentOrder);
                                dialog.hide();
                            }
                            else {
                                orderTyper.setText("Minimum is 1");
                            }
                        } else {
                            orderTyper.setText("Not enough bucks");
                        }
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });

            }
        });

        //Sell BTN
        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(parent.getContext());
                dialog.setContentView(R.layout.dialog);
                orderTyper = dialog.findViewById(R.id.order_buyorsell);
                TextView orderStockName = dialog.findViewById(R.id.order_stockname);
                TextView orderPrice = dialog.findViewById(R.id.order_price);
                EditText orderAmount = dialog.findViewById(R.id.order_amounttobuyorsell);
                orderAmount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if (s.length() > 0) {
                            orderAmountAsInteger = Integer.valueOf(s.toString());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                orderTyper.setText("Order: Sell");
                orderStockName.setText("Stock: " + stockList.get(position).getName());
                orderPrice.setText("Price: " + stockList.get(position).getValue());

                dialog.show();

                Button btn_proceed = dialog.findViewById(R.id.order_proceed);
                Button btn_cancel = dialog.findViewById(R.id.order_cancel);
                btn_proceed.setText("Sell");

                btn_proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //        //Write a message to the database
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("users").child(athenticationString);
                        Order currentOrder = new Order(stockList.get(position), false, orderAmountAsInteger, orderAmountAsInteger*stockList.get(position).getValue() );
                        if (currentOrder.getAmountOfStockToBuy() <= stockList.get(position).getAmountOwned()) {
                            if (currentOrder.getAmountOfStockToBuy() > 0) {
                                myRef.child("myorders").push().setValue(currentOrder);

                                dialog.hide();
                            } else {
                                orderTyper.setText("Minimum is 1");
                            }


                        } else {
                            orderTyper.setText("Not enough stock");
                        }
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });




                //MISSING SENDING THE SELL/BUY ORDER AND CLOSE THE DIALOG!

            }
        });


        return convertView;
    }

    public listviewAdaptor(@NonNull Context context, int resource, ArrayList<Stock> stockList, String athenticationString, double bank) {
        super(context, resource, stockList);
        this.con = context;
        this.stockList = stockList;
        this.athenticationString = athenticationString;
        this.userbank = bank;

    }


}
