package com.example.hp1.myfinalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ChemInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chem_info);
        Intent intent=getIntent();
        int num=intent.getIntExtra("num",0);
        CustomElementRow adapter=new CustomElementRow(this);
        String[] infos=getResources().getStringArray(R.array.info)
                ,englishName=getResources().getStringArray(R.array.element_name_eng)
                ,yearDiscovred=getResources().getStringArray(R.array.year);
        El_Info[] el_infos={new El_Info(infos[0],englishName[num]),new El_Info(infos[1],yearDiscovred[num])};
        for(int i=0;i<el_infos.length;i++)
            adapter.add(el_infos[i]);
        ListView lv=(ListView)findViewById(R.id.lvKnowledge);
        lv.setAdapter(adapter);
    }
}
