package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    Button btsave;
    private static final int CAMERA_REQUEST =123 ;
    ImageView imageView;
    int PICK_IMAGE=100;
    Uri imageUri;
    DataBaseRegister db;
    TextView tv;
    Intent intent;
    Cursor res;
    String _id,firstName,lastName,Email,Password,takhassos,engpoint,mathpoints,grade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btsave=(Button)findViewById(R.id.btsave);
        btsave.setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        tv=(TextView)findViewById(R.id.tvRegister);
        db=new DataBaseRegister(this);
        res=db.getAllData();
        StringBuffer stringBuffer=new StringBuffer();
        intent=getIntent();
        if(res!=null&&res.getCount()>0)
        while (res.moveToNext())
            if(res.getString(3).equals(intent.getStringExtra("username from mainActivity")))
            {
                stringBuffer.append("id: "+res.getString(0)+"\n");
                _id=res.getString(0);
                stringBuffer.append("first name: "+res.getString(1)+"\n");
                firstName=res.getString(1);
                stringBuffer.append("last name: "+res.getString(2)+"\n");
                lastName=res.getString(2);
                stringBuffer.append("Email: "+res.getString(3)+"\n");
                Email=res.getString(3);
                stringBuffer.append("password: "+res.getString(4)+"\n");
                Password=res.getString(4);
                stringBuffer.append("takhassos: "+res.getString(5)+"\n");
                takhassos=res.getString(5);
                stringBuffer.append("eng points: "+res.getString(6)+"\n");
                engpoint=res.getString(6);
                stringBuffer.append("math points: "+res.getString(7)+"\n");
                mathpoints=res.getString(7);
                stringBuffer.append("grade: "+res.getString(8));
                grade=res.getString(8);
                tv.setText(stringBuffer.toString());
                Bitmap b = BitmapFactory.decodeByteArray(res.getBlob(9),0,res.getBlob(9).length);
                imageView.setImageBitmap(b);
            }
            res.moveToFirst();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }else
            if(resultCode==RESULT_OK&&requestCode==PICK_IMAGE)
            {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
    }

    @Override
    public void onClick(View v) {
        if(v==imageView){
            SelectImage();
        }else
            if(v==btsave)
            {
                Intent i = new Intent(this, MainActivity.class);
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap b = drawable.getBitmap();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 50, bs);
                byte[] imageInByte=bs.toByteArray();
                i.putExtra("byteArray", imageInByte);
                i.putExtra("boolean",true);
                db.updateRegisterData(_id,firstName,lastName,Email,Password,takhassos,Integer.parseInt(engpoint),Integer.parseInt(mathpoints),Integer.parseInt(grade),imageInByte);
                i.putExtra("username from register",intent.getStringExtra("username from mainActivity"));
                startActivity(i);

            }
        finish();
    }

    public void openGallery(){
        Intent gallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    private void SelectImage()
    {
        final CharSequence[] items={"Camera","Gallery","Cancel"};

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Add Image");
        alertdialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST);
                        break;
                    case 1:
                       openGallery();
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        });
        alertdialog.show();
    }
}
