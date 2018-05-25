package com.example.hp1.myfinalproject.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp1.myfinalproject.R;

/**
 * Created by Dell on 5/17/2018.
 */

public class CustomGrid extends BaseAdapter {

    Context context;
    int[] images;
    String[] subjects;
    LayoutInflater layoutInflater;

    public CustomGrid(Context context, int[] images, String[] subjects) {
        this.context = context;
        this.images = images;
        this.subjects = subjects;
    }

    @Override
    public int getCount() {
        return subjects.length;
    }

    @Override
    public Object getItem(int i) {
        return subjects[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view=convertView;

        if(view==null)
        {
            layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.custom_grid,null);
        }

        ImageView imageView=(ImageView)view.findViewById(R.id.imageView2);
        TextView textView=(TextView)view.findViewById(R.id.subjectText);

        imageView.setImageResource(images[i]);
        textView.setText(subjects[i]);


        return view;
    }
}
