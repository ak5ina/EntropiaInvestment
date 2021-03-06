package com.scrippy.entropiainvestment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scrippy.entropiainvestment.adapters.MyIntrestAdaptor;
import com.scrippy.entropiainvestment.adapters.MyOrderlistviewAdaptor;
import com.scrippy.entropiainvestment.adapters.listviewAdaptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText textviewEmail, textviewPassword, btcinputadress;
    private Button btn_login, btn_createAccount, btn_MyOrders, btn_MyIntrest, btn_btcadress;
    private FirebaseAuth mAuth;
    private TextView overViewUserMail, overViewUserBank;
    private ListView listView;
    private StockList stocklist;
    private User logedInUser;
    private FirebaseUser firebaseUser;
    private ArrayList<Order> testList;
    private com.scrippy.entropiainvestment.adapters.listviewAdaptor listviewAdaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hide action bar
        getSupportActionBar().hide();

        //Init
        logedInUser = new User();
        mAuth = FirebaseAuth.getInstance();
        textviewEmail = findViewById(R.id.login_email);
        textviewPassword = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.login_btn_login);
        btn_createAccount = findViewById(R.id.login_btn_createaccount);

        //Making btn functions:
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textviewEmail.getText().length() > 0 && textviewPassword.getText().length() > 0) {
                    SignIn();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter both email and password", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });



    }

    private void SignIn() {
        mAuth.signInWithEmailAndPassword(textviewEmail.getText().toString(), textviewPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(getApplicationContext(), "Login succeed", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), user.getUid(), Toast.LENGTH_LONG).show();

                            GetDateToUser(user);
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void GetDateToUser(FirebaseUser user){
        //Changing UI to the logged in UI.
        setContentView(R.layout.activity_over_view);
        firebaseUser = user;

        //init main screen
        overViewUserMail = findViewById(R.id.textview_profile_name);
        overViewUserBank = findViewById(R.id.textview_bucksamount);
        listView = findViewById(R.id.listview);
        stocklist = new StockList();

        overViewUserMail.setText("Getting data");


        updateStockList();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            GetDateToUser(currentUser);
        }
    }

    private void updateStockList(){
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
                        stocklist.getListOfStocks().add(new Stock(Double.parseDouble(secondChildSnapShot.child("value").getValue().toString()), secondChildSnapShot.child("name").getValue().toString(),secondChildSnapShot.child("info").getValue().toString()));
                    }
                }

                updateUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateUser(){
        //update
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Getting user
        DatabaseReference myRef = database.getReference("users").child(mAuth.getUid());
//        myRef.keepSynced(true);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    logedInUser.setUserEmail(dataSnapshot.child("userEmail").getValue().toString());
                    logedInUser.setBank(Double.valueOf(dataSnapshot.child("bank").getValue().toString()));
                    //ADDING STOCKS TO PORTFOLIO
                    int i = 0;
                    //PUTTIN IN THE PORTFOLIO
                    if (childDataSnapshot.getKey().contains("portfolio")) {
                        logedInUser.getPortfolio().getStockYouOwn().clear();
                        for (DataSnapshot secoundChild : childDataSnapshot.getChildren()) {
                            logedInUser.getPortfolio().getStockYouOwn().add(Integer.valueOf(secoundChild.getValue().toString()));
                            stocklist.getListOfStocks().get(i).setAmountOwned(Integer.valueOf(secoundChild.getValue().toString()));
                            stocklist.getListOfStocks().get(i).setCombinedValueOfAllOwnedStock(
                                    (Integer.valueOf(secoundChild.getValue().toString())) * stocklist.getListOfStocks().get(i).getValue()
                            );
                            i++;
                        }
                    }
//                    PUTTING IN THE ORDERS
                    if (childDataSnapshot.getKey().contains("orders")) {
                        logedInUser.getMyOrders().clear();
                        for (DataSnapshot secoundChild : childDataSnapshot.getChildren()) {


                            Stock stockToBuyOrSell = new Stock(Double.parseDouble(secoundChild.child("stockToBuyOrSell").child("value").getValue().toString()), secoundChild.child("stockToBuyOrSell").child("name").getValue().toString(), secoundChild.child("stockToBuyOrSell").child("info").getValue().toString());
                            boolean isThisABuyOrder = Boolean.parseBoolean(secoundChild.child("thisABuyOrder").getValue().toString());
                            int amountOfStockToBuy = Integer.valueOf(secoundChild.child("amountOfStockToBuy").getValue().toString());
                            double totalPrice = Integer.valueOf(secoundChild.child("totalPrice").getValue().toString());
                            boolean isCompleted =  Boolean.parseBoolean(secoundChild.child("completed").getValue().toString());
                            boolean isCanceled =  Boolean.parseBoolean(secoundChild.child("canceled").getValue().toString());
                            String orderID =  secoundChild.getKey();

                            if (!isCanceled) {
                                logedInUser.getMyOrders().add(new Order(stockToBuyOrSell, isThisABuyOrder, amountOfStockToBuy, totalPrice, isCompleted, orderID, isCanceled));
                            }
                        }
                    }

                    //HERE WERE ADDINGING INTREST
                    if (childDataSnapshot.getKey().contains("intrestpayouts")) {
                        logedInUser.getIntrestPayouts().clear();
                        for (DataSnapshot secoundChild : childDataSnapshot.getChildren()) {
                            logedInUser.getIntrestPayouts().add(new IntrestPayout(Double.parseDouble(secoundChild.child("buckspayout").getValue().toString()),secoundChild.child("date").getValue().toString(), secoundChild.child("info").getValue().toString(),secoundChild.child("type").getValue().toString() ));
                        }
                    }


                }
                if (mAuth.getUid().contains("Pwoy6RVFm2NhXcTHGLuYSe9QFL33")){
                    AdminLogin();
                } else {
                    UpdateUI();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void AdminLogin() {
        setContentView(R.layout.activity_over_view);


    }

    private void UpdateUI() {

        overViewUserMail.setText(logedInUser.getUserEmail());
        overViewUserBank.setText(("Bucks: " + logedInUser.getBank()));



        listviewAdaptor = new listviewAdaptor(getApplicationContext(), R.layout.listview_object, stocklist.getListOfStocks(), mAuth.getUid(), logedInUser.getBank());
        listView.setAdapter(listviewAdaptor);

        //Setting up orders
        btn_MyOrders = findViewById(R.id.btn_orders);
        btn_MyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialogmyorders, null);

                //Opdate listview
                ListView lv = view.findViewById(R.id.my_order_listview);
                //Listview adapter
                MyOrderlistviewAdaptor lv2Adapter = new MyOrderlistviewAdaptor(getApplicationContext(), R.layout.listview_order_object, logedInUser.getMyOrders(), mAuth.getUid());
                //Listview set listviewadapter
                lv.setAdapter(lv2Adapter);

                builder.setView(view);
                builder.create().show();



            }
        });

        btn_MyIntrest = findViewById(R.id.btn_intrest);
        btn_MyIntrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialogmyorders, null);

                TextView head = view.findViewById(R.id.my_order_head);
                head.setText("My intrest");

                //Opdate listview
                ListView lv = view.findViewById(R.id.my_order_listview);
                //Listview adapter
                MyIntrestAdaptor lv2Adapter = new MyIntrestAdaptor(getApplicationContext(), R.layout.listview_intrest_object, logedInUser.getIntrestPayouts(), mAuth.getUid());
                //Listview set listviewadapter
                System.out.println(logedInUser.getIntrestPayouts().size());
                lv.setAdapter(lv2Adapter);

                builder.setView(view);
                builder.create().show();



            }
        });



        btn_btcadress = findViewById(R.id.btn_bitcoin);
        btn_btcadress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_bitcoin, null);

                builder.setView(view);
                final Dialog dialog = builder.create();
                dialog.show();

                Button btn_confirm_btc_adress = view.findViewById(R.id.btn_btc_confirm);
                Button btn_cancel_btc_adress = view.findViewById(R.id.btn_btc_cancel);

                btcinputadress = view.findViewById(R.id.et_btc_adress);

                btn_confirm_btc_adress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //        //Write a message to the database
                        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                        DatabaseReference myRef2 = database2.getReference("users").child(mAuth.getUid()).child("btcadr");
                        myRef2.setValue(btcinputadress.getText().toString());
                        dialog.hide();
                    }
                });


                btn_cancel_btc_adress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });



            }
        });



    }



}