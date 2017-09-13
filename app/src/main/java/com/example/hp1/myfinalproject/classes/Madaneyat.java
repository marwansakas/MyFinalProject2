package com.example.hp1.myfinalproject.classes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hp1.myfinalproject.Madaneyat_Video;
import com.example.hp1.myfinalproject.R;

public class Madaneyat extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView lv1;
    ArrayAdapter<String> adapter;
    String firstpart="android.resource://com.example.hp1.myfinalproject/";
    int[] videoPaths={R.raw.ale3lam_wa_elseyase_fe_israel,R.raw.alhokom_almahalle,R.raw.alkawmeye,
    R.raw.alsolta_alkada2eye,R.raw.alsolta_altanfezeye,R.raw.alsolta_altashre3eye,R.raw.dawlat_israel_walsha3b_alyahode_fe_alshetat,R.raw.demokrateye_ta3refat_tawajohat,
    R.raw.hasem_aktareye,R.raw.hodod_aldemokrateye,R.raw.hokok_egtema3eye,R.raw.hokom_alsha3b,R.raw.karar_taksem_181,R.raw.mabda2_soltat_alkanon,
    R.raw.mabda2_takdem_alsolta,R.raw.mafahemtawjohat_demokrateye_libraliye,R.raw.momayezat_alakaleye_alkawmeye,R.raw.momayezat_israel_kadawle_kawmeye,
    R.raw.ta3adodeye,R.raw.tasado3_kawme,R.raw.wathekat_esteklal};
    String[] videoName={"الإعلام والسياسة في إسرائيل","الحقوق الطبيعية والسياسية","الحكم المحلّي","القومية","السلطة القضائية","السلطة التنفيذية",
    "السلطة التشريعية","دولة إسرائيل والشعب اليهودي في الشتات","الديمقراطية تعريفات وتوجهات","حسم الاكثرية","حدود الديمقراطية","الحقوق الاجتماعية",
    "حكم الشعب","قرار التقسيم رقم 181","مبدأ سلطة القانون","مبدأ تقييد السلطة","مفاهيم (توجهات) ديمقراطية ليبرالية","مميّزات الأقليات القومية",
    "مميزات إسرائيل كدولة يهودية","التعددية","التصدّع القومي","وثيقة الاستقلال"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madaneyat);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,videoName);
        lv1=(ListView)findViewById(R.id.listView);
        lv1.setOnItemClickListener(Madaneyat.this);
        lv1.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this,Madaneyat_Video.class);
        intent.putExtra("videoPath",firstpart+videoPaths[i]);
        startActivity(intent);
    }
}
