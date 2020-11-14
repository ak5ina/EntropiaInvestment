package com.scrippy.entropiainvestment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scrippy.entropiainvestment.adapters.MyOrderlistviewAdaptor;

import java.util.ArrayList;

public class Admin_Confirm_Order extends AppCompatActivity {

    ArrayList<Order> orderListToCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__confirm__order);
        getSupportActionBar().hide();

        orderListToCheck = new ArrayList<>();
        GetOrderToConfirm();
    }

    private void GetOrderToConfirm() {

        //update
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Getting user
        DatabaseReference myRef = database.getReference("users");
//public Order(Stock stockToBuyOrSell, boolean isThisABuyOrder, int amountOfStockToBuy, double totalPrice, boolean isCompleted, String orderID, boolean isCanceled) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot.child("myorders").getChildrenCount() > 0) {
                        for (DataSnapshot secoundChild : childDataSnapshot.child("myorders").getChildren()) {
                            if (!Boolean.parseBoolean(secoundChild.child("completed").getValue().toString()) && !Boolean.parseBoolean(secoundChild.child("canceled").getValue().toString())) {
                                User userToStock = new User();
                                userToStock.setBank(Double.parseDouble(childDataSnapshot.child("bank").getValue().toString()));
                                userToStock.setAuthUID(childDataSnapshot.getKey());
                                orderListToCheck.add(new Order(secoundChild.child("stockToBuyOrSell").child("name").getValue().toString(),
                                        Boolean.parseBoolean(secoundChild.child("thisABuyOrder").getValue().toString()),
                                        Integer.parseInt(secoundChild.child("amountOfStockToBuy").getValue().toString()),
                                        Double.parseDouble(secoundChild.child("totalPrice").getValue().toString()),
                                        Boolean.parseBoolean(secoundChild.child("completed").getValue().toString()),
                                        secoundChild.getKey(),
                                        Boolean.parseBoolean(secoundChild.child("canceled").getValue().toString()),
                                        userToStock,
                                        secoundChild.child("stockToBuyOrSell").child("stockID").getValue().toString()
                                        ));
                            }
                            System.out.println(secoundChild.child("stockToBuyOrSell").child("name").getValue().toString());
                        }
                    }
                }
                System.out.println(orderListToCheck.size());
                SortListInListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void SortListInListView() {

        ListView lv = findViewById(R.id.my_order_listview_admin);
        //Listview adapter
        MyOrderlistviewAdaptor lv2Adapter = new MyOrderlistviewAdaptor(getApplicationContext(), R.layout.listview_order_object, orderListToCheck);
        //Listview set listviewadapter
        lv.setAdapter(lv2Adapter);

    }
}