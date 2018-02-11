package com.example.hp1.myfinalproject;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;


public class ChemInfo extends AppCompatActivity {

    Toolbar tb;
    CollapsingToolbarLayout ctl;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chem_info);

        Intent intent=getIntent();
        int num=intent.getIntExtra("num",0);
        String[] symbols=getResources().getStringArray(R.array.element_name_eng);
        String symbol=symbols[num];

        tb = (Toolbar) findViewById(R.id.tb);
        ctl = (CollapsingToolbarLayout) findViewById(R.id.collapseit);
        appBarLayout=(AppBarLayout)findViewById(R.id.appbar);
        setSupportActionBar(tb);
        ctl.setTitle(symbol);

        appBarLayout.setBackgroundResource(getResources().getIdentifier(symbol.toLowerCase(),"mipmap",getPackageName()));

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);

        String[] infos=getResources().getStringArray(R.array.info);
        String[][] details={getResources().getStringArray(R.array.element_name_eng),getResources().getStringArray(R.array.element_name_latin),getResources().getStringArray(R.array.year)};
        for(int i=0;i<3;i++)
            adapter.add(infos[i]+":"+"\n"+details[i][num]);
        ListView lv=(ListView)findViewById(R.id.lvKnowledge);
        lv.setAdapter(adapter);
        //setListViewHeightBasedOnChildren(lv);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
