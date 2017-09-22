package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    Button btsave;
    private static final int CAMERA_REQUEST =123 ;
    ImageView imageView;
    int PICK_IMAGE=100;
    Uri imageUri;
    TextView tv;

    DatabaseReference databaseReferenceProfile;
    StorageReference storageReference;
    StorageReference file_path;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView = (ImageView) findViewById(R.id.imageView);
        btsave=(Button)findViewById(R.id.btsave);
        tv=(TextView)findViewById(R.id.tvRegister);

        btsave.setOnClickListener(this);
        imageView.setOnClickListener(this);

        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        file_path=storageReference.child("Photos").child(firebaseUser.getUid());
        databaseReferenceProfile= FirebaseDatabase.getInstance().getReference("Registrations");

        file_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Profile.this)
                        .load(uri)
                        .into(imageView);
            }
        });

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
                imageUri=data.getData();
                file_path=storageReference.child("Photos").child(firebaseUser.getUid());
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
                if(imageUri!=null){
                    file_path.putFile(imageUri);
                    i.putExtra("uri",imageUri.toString());
                    i.putExtra("check",true);
                }
                startActivity(i);
                finish();
            }
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
