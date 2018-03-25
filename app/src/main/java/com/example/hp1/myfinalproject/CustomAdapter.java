package com.example.hp1.myfinalproject;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp1.myfinalproject.JavaClasses.wazefe;

class CustomAdapter extends ArrayAdapter<wazefe>{

	//this Class has been commented all over

	TextView mawdo3,matlob,day;
	ImageView im;
	
	String[] S={"Math","Arabic","Hebrew","English","Computers","Biology","Chemistry","History"};
	int[] pics={R.drawable.math,R.drawable.arabic,R.drawable.hebrew,R.drawable.english,R.drawable.coding,R.drawable.bio,R.drawable.chemistry,R.drawable.history};

	public CustomAdapter(Context context, ArrayList<wazefe> w) {
		super(context,R.layout.custom_row ,w);
	}


	/**
	 * set all the received info in the right places
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
		LayoutInflater sakas=LayoutInflater.from(getContext());
		View customview=sakas.inflate(R.layout.custom_row,parent, false);

		mawdo3=(TextView)customview.findViewById(R.id.textView1);//initialize mawdo3
		matlob=(TextView)customview.findViewById(R.id.textView2);//initialize matlob
		day=(TextView)customview.findViewById(R.id.textView3);//initialize day
		im=(ImageView)customview.findViewById(R.id.imageView1);//initialize im

		String subject=getItem(position).getSubject(),details=getItem(position).getDetails();//get the dtials and subject
		String d=getItem(position).getD().getDay()+"/"+getItem(position).getD().getMonth()+"/"+getItem(position).getD().getYear();//get the day/month/ear
		
		mawdo3.setText(subject);//show th subject
		matlob.setText(details);//show the matlob
		day.setText(d);//show the date

		for(int i=0;i<S.length;i++)//pick the right image for the subject
		{
			if(S[i].equals(subject))
			{
				im.setImageResource(pics[i]);
				break;
			}
		}

		return customview;//return the row
	}

}
