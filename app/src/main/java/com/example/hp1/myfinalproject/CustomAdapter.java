package com.example.hp1.myfinalproject;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter<wazefe>{

	TextView mawdo3,matlob,day;
	ImageView im;
	
	String[] S={"Math","Arabic","Hebrew","English","Computers","Biology","Chemistry","History"};
	int[] pics={R.drawable.math,R.drawable.arabic,R.drawable.hebrew,R.drawable.english,R.drawable.coding,R.drawable.bio,R.drawable.chemistry,R.drawable.history};

	public CustomAdapter(Context context, ArrayList<wazefe> w) {
		super(context,R.layout.custom_row ,w);
	}

	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
		LayoutInflater sakas=LayoutInflater.from(getContext());
		View customview=sakas.inflate(R.layout.custom_row,parent, false);

		mawdo3=(TextView)customview.findViewById(R.id.textView1);
		matlob=(TextView)customview.findViewById(R.id.textView2);
		day=(TextView)customview.findViewById(R.id.textView3);
		im=(ImageView)customview.findViewById(R.id.imageView1);

		String subject=getItem(position).getSubject(),details=getItem(position).getDetails();
		String d=getItem(position).getD().getDay()+"/"+getItem(position).getD().getMonth()+"/"+getItem(position).getD().getYear();
		
		mawdo3.setText(subject);
		matlob.setText(details);
		day.setText(d);

		for(int i=0;i<S.length;i++)
		{
			if(S[i].equals(subject))
			{
				im.setImageResource(pics[i]);
				break;
			}
		}

		return customview;
	}

}
