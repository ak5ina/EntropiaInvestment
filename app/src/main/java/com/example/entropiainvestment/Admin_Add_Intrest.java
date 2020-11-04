package com.example.entropiainvestment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_Add_Intrest extends AppCompatActivity {

    Spinner stockSpinner;
    ArrayList<Stock> listOfStock;
    ArrayList<User> userList;
    ArrayList<IntrestPayout> intrestList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    int intNumberForStockToLookAt;

    EditText etAmountA100Share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__add__intrest);

        etAmountA100Share = findViewById(R.id.text_add_intrest_amount);
        database = FirebaseDatabase.getInstance();
        //Getting user
        myRef = database.getReference("users");

        listOfStock = new ArrayList<>();

        GetAllStock();


        Button btn = findViewById(R.id.btn_confirm_new_intrest);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intNumberForStockToLookAt = UpdateSpinnerNumber();
                if (etAmountA100Share.getText().length() > 0 && intNumberForStockToLookAt < 400) {
                    userList = new ArrayList<>();

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                User userToList = new User();

                                for (DataSnapshot secoundChild : childDataSnapshot.getChildren()) {
                                    if (secoundChild.getKey().contains("portfolio")) {
                                        for (DataSnapshot thirdChild : secoundChild.getChildren()) {
                                            userToList.getPortfolio().getStockYouOwn().add(Integer.valueOf(thirdChild.getValue().toString()));
                                        }
                                    }
                                    if (secoundChild.getKey().contains("bank")) {
                                        userToList.setBank(Double.valueOf(secoundChild.getValue().toString()));
                                    }
                                }

                                userToList.setAuthUID(childDataSnapshot.getKey());

                                userList.add(userToList);
                            }

                            TEST();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });




    }

    private int UpdateSpinnerNumber() {
        if (stockSpinner.getSelectedItem().toString().contains("Ancient Greece")){
            return 0;
        } else if (stockSpinner.getSelectedItem().toString().contains("Crystal Palace")){
            return 1;
        } else if (stockSpinner.getSelectedItem().toString().contains("Arkadia Moon")){
            return 2;
        } else if (stockSpinner.getSelectedItem().toString().contains("Arkadia Underground")){
            return 3;
        } else if (stockSpinner.getSelectedItem().toString().contains("Calypso Land")){
            return 4;
        } else {
            return 420;
        }


    }

    private void TEST() {
        intrestList = new ArrayList<>();

        EditText etAmountA100Share = findViewById(R.id.text_add_intrest_amount);
        Double amountA100Share = Double.valueOf(etAmountA100Share.getText().toString());
        Double amountAShare = 0.0;
        if (amountA100Share > 0){
            amountAShare = (amountA100Share/10);
        } else {
            Toast.makeText(this,"ERROR ON AMOUNT", Toast.LENGTH_SHORT);
        }

        EditText etIntrestInfo = findViewById(R.id.text_add_intrest_info);

        for (User userToLookAt : userList){
//
//            IntrestPayout payout = new IntrestPayout((amountAShare*userToLookAt.getIntrestPayouts().get()))
            System.out.println(intNumberForStockToLookAt);

            System.out.println("TESTER" + userToLookAt.getAuthUID());

        }


//        //Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        //Getting user
//        DatabaseReference myRef = database.getReference("users");


//
//        User user2 = new User(user.getEmail(),0);
//        Portfolio portfolio = new Portfolio(0,0,1,0);
//        user2.setPortfolio(portfolio);
//
//        myRef.child(user.getUid()).setValue(user2);

    }

    private void GetAllStock() {
        //update
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Getting stocks
        DatabaseReference myRef = database.getReference("stocks");
//        myRef.keepSynced(true);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot secondChildSnapShot : childDataSnapshot.getChildren()){
                        listOfStock.add(new Stock(Double.parseDouble(secondChildSnapShot.child("value").getValue().toString()), secondChildSnapShot.child("name").getValue().toString(),secondChildSnapShot.child("info").getValue().toString()));
                    }
                }

                SetUpSpinner();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void SetUpSpinner() {

        stockSpinner = (Spinner) findViewById(R.id.spinnerStock);
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < listOfStock.size(); i++){
            list.add(listOfStock.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stockSpinner.setAdapter(dataAdapter);

    }
}