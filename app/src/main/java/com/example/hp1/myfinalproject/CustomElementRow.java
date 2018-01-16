package com.example.hp1.myfinalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CustomElementRow extends ArrayAdapter<El_Info> {

    TextView tv1,tv2;

    public CustomElementRow(Context context/*, ArrayList<El_Info> el_infos*/) {
        super(context, R.layout.custom_element_row/*, el_infos*/);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customRow = inflater.inflate(R.layout.custom_element_row, parent, false);

        tv1 = (TextView) customRow.findViewById(R.id.textView2);
        tv2 = (TextView) customRow.findViewById(R.id.textView3);
        tv1.setText(getItem(position).getInfo());
        tv2.setText(getItem(position).getDetails());
        return customRow;
    }
}

