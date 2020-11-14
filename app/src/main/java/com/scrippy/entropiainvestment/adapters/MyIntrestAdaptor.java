package com.scrippy.entropiainvestment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scrippy.entropiainvestment.IntrestPayout;
import com.scrippy.entropiainvestment.R;

import java.util.ArrayList;

public class MyIntrestAdaptor extends ArrayAdapter<IntrestPayout> {
    private  ArrayList<IntrestPayout> intrestList;
    private Context con;
    private String athenticationString;


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_intrest_object, parent, false);
        }

        TextView tvIntrestType = convertView.findViewById(R.id.my_intrest_name_of_activity);
        TextView tvIntrestDate = convertView.findViewById(R.id.my_intrest_name_of_stock);
        TextView tvIntrestAmount = convertView.findViewById(R.id.my_intrest_amount);
        TextView tvIntrestinfo = convertView.findViewById(R.id.my_intrest_info);

        tvIntrestType.setText("Type: " + intrestList.get(position).getType());
        tvIntrestDate.setText("Date: " + intrestList.get(position).getDate());
        tvIntrestAmount.setText("Amount: " + intrestList.get(position).getBuckspayout());
        tvIntrestinfo.setText("Info: " + intrestList.get(position).getInfo());




            return convertView;
    }

    public MyIntrestAdaptor(@NonNull Context context, int resource, ArrayList<IntrestPayout> intrestList, String athenticationString) {
        super(context, resource, intrestList);
        this.con = context;
        this.intrestList = intrestList;
        this.athenticationString = athenticationString;

    }


}
