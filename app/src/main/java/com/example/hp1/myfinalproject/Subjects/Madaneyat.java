package com.example.hp1.myfinalproject.Subjects;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.CalendarActivity;
import com.example.hp1.myfinalproject.Explanation;
import com.example.hp1.myfinalproject.Graphs.Circle;
import com.example.hp1.myfinalproject.Login;
import com.example.hp1.myfinalproject.Madaneyat_Video;
import com.example.hp1.myfinalproject.MainActivity;
import com.example.hp1.myfinalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Madaneyat extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView lv1;
    ArrayAdapter<String> adapter;
    FirebaseAuth firebaseAuth;
    String firstpart="android.resource://com.example.hp1.myfinalproject/";
    int[] videoPaths={R.raw.ale3lam_wa_elseyase_fe_israel,R.raw.alhokom_almahalle,R.raw.alkawmeye,
    R.raw.alsolta_alkada2eye,R.raw.alsolta_altanfezeye,R.raw.alsolta_altashre3eye,R.raw.dawlat_israel_walsha3b_alyahode_fe_alshetat,R.raw.demokrateye_ta3refat_tawajohat,
    R.raw.hasem_aktareye,R.raw.hodod_aldemokrateye,R.raw.hokok_egtema3eye,R.raw.hokom_alsha3b,R.raw.karar_taksem_181,R.raw.mabda2_soltat_alkanon,
    R.raw.mabda2_takdem_alsolta,R.raw.mafahemtawjohat_demokrateye_libraliye,R.raw.momayezat_alakaleye_alkawmeye,R.raw.momayezat_israel_kadawle_kawmeye,
    R.raw.ta3adodeye,R.raw.tasado3_kawme,R.raw.wathekat_esteklal};//the video path from raw folder
    String[] videoName={"الإعلام والسياسة في إسرائيل","الحقوق الطبيعية والسياسية","الحكم المحلّي","القومية","السلطة القضائية","السلطة التنفيذية",
    "السلطة التشريعية","دولة إسرائيل والشعب اليهودي في الشتات","الديمقراطية تعريفات وتوجهات","حسم الاكثرية","حدود الديمقراطية","الحقوق الاجتماعية",
    "حكم الشعب","قرار التقسيم رقم 181","مبدأ سلطة القانون","مبدأ تقييد السلطة","مفاهيم (توجهات) ديمقراطية ليبرالية","مميّزات الأقليات القومية",
    "مميزات إسرائيل كدولة يهودية","التعددية","التصدّع القومي","وثيقة الاستقلال"};//the name of the slides/videos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madaneyat);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,videoName);//initialize adapter
        lv1=(ListView)findViewById(R.id.listView);//initialize lv1
        lv1.setOnItemClickListener(Madaneyat.this);//make lv1 clickable
        lv1.setAdapter(adapter);//set the adapter to lv1
        firebaseAuth= FirebaseAuth.getInstance();//to initialize firebaseAuth
    }

    /**
     * takes you to Madaneyat_Video to show the chosen video
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this,Madaneyat_Video.class);//initialize intent
        intent.putExtra("videoPath",firstpart+videoPaths[i]);//add the string path to intent
        startActivity(intent);//start activity
        finish();
    }

    /**
     *
     * @param menu the menu
     * @return
     * this function create the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//to create an options menu
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();//initialize menuInflater
        menuInflater.inflate(R.menu.menu_main, menu);//to create the three dot menu

        return super.onCreateOptionsMenu(menu);//to return if the result
    }

    /**
     * if the user clicked logout then the user will be logged out of the application
     * if he chooses delete his account will be deleted
     * @param item thid=s parameter is the item that was clicked on
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)//to make the items for the options menu
    {
        switch (item.getItemId()) {
            case R.id.logOut:
                firebaseAuth.signOut();
                startActivity(new Intent(Madaneyat.this, Login.class));
                finish();
                return true;
            case R.id.delete:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"user was deleted",Toast.LENGTH_SHORT);
                                }
                            }
                        });
                startActivity(new Intent(Madaneyat.this, Login.class));
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);//return the items for the menu
    }

    /**
     * go back to explinations page
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Explanation.class));
    }
}
