package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp1.myfinalproject.Graphs.Circle;
import com.example.hp1.myfinalproject.JavaClasses.InformationRegistered;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    Button btsave;
    private static final int CAMERA_REQUEST =123;
    private static final int PICK_IMAGE=100;
    ImageView imageView;

    Uri imageUri;
    byte[] dataBAOS;

    ListView lvInfo;
    ArrayList arrayInfo=new ArrayList();
    ArrayAdapter adapter;

    DatabaseReference databaseReferenceProfile;
    StorageReference storageReference;
    StorageReference file_path;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView = (ImageView) findViewById(R.id.imageView);
        btsave=(Button)findViewById(R.id.btsave);
        lvInfo=(ListView)findViewById(R.id.lvProInfo);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayInfo){
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }};
        lvInfo.setAdapter(adapter);

        btsave.setOnClickListener(this);
        imageView.setOnClickListener(this);

        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        file_path=storageReference.child("Photos").child(firebaseUser.getUid());

        file_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Profile.this)
                        .load(uri)
                        .into(imageView);
            }
        });

        databaseReferenceProfile= FirebaseDatabase.getInstance().getReference("Registrations").child("Students").child(firebaseUser.getUid());
        firebaseAuth=FirebaseAuth.getInstance();//to initialize firebaseAuth
    }

    /**
     * shows the user's information that was added when he signed up for an account
     */
    @Override
    protected void onStart() {
        super.onStart();
        databaseReferenceProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                InformationRegistered informationRegistered=dataSnapshot.getValue(InformationRegistered.class);
                arrayInfo.add("ID: "+informationRegistered.get_Id());
                arrayInfo.add("First Name: "+informationRegistered.getFirstName());
                arrayInfo.add("Last Name: "+informationRegistered.getLastName());
                arrayInfo.add("Email: "+informationRegistered.getEmail());
                arrayInfo.add("PassWord: "+informationRegistered.getPassWord());
                arrayInfo.add("Takhassos: "+informationRegistered.getTakhassos());
                arrayInfo.add("English Points: "+informationRegistered.getEngPoints());
                arrayInfo.add("Math Points: "+informationRegistered.getMathPoints());
                arrayInfo.add("Grade: "+informationRegistered.getGrade());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     *
     * @param requestCode the code that identifies what the action is
     * @param resultCode if the action was possibale
     * @param data and the data that was recived from the action
     * this function displays the image that was taken either from camera or gallary
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            dataBAOS = baos.toByteArray();
            file_path=storageReference.child("Photos").child(firebaseUser.getUid());
            imageView.setImageBitmap(photo);
        }else
            if(resultCode==RESULT_OK&&requestCode==PICK_IMAGE)
            {
                imageUri=data.getData();
                file_path=storageReference.child("Photos").child(firebaseUser.getUid());
                imageView.setImageURI(imageUri);
            }
    }

    /**
     * this function lets the user open the alertDialog
     * and save the information that was changed
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v==imageView){
            SelectImage();
        }else
            if(v==btsave)
            {
                saveUserInformation();
            }
    }

    /**
     * chose an alertDialog with three option to let you chose
     * if to take picture from camera, gallary, or not at all
     */
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
                        Intent gallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery,PICK_IMAGE);
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        });
        alertdialog.show();
    }

    /**
     * this makes the image appear in the MainActivity
     */
    public void saveUserInformation()
    {
        Intent i = new Intent(this, MainActivity.class);
        if(imageUri!=null){
            file_path.putFile(imageUri);
            i.putExtra("uri",imageUri.toString());
            i.putExtra("checkUri",true);
        }
        if(dataBAOS!=null)
        {
            file_path.putBytes(dataBAOS);
            i.putExtra("byte[]",dataBAOS);
            i.putExtra("checkByte[]",true);
        }
        startActivity(i);
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
                startActivity(new Intent(Profile.this, Login.class));
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
                startActivity(new Intent(Profile.this, Login.class));
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);//return the items for the menu
    }

    /**
     * go back to MainActivity page
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }

}
