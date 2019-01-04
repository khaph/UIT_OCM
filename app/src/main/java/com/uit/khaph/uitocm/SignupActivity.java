package com.uit.khaph.uitocm;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    TextInputLayout edtUserName;
    TextInputLayout edtPassWord;
    TextInputLayout edtDayOfBirth;
    TextInputLayout edtClass;
    TextInputLayout edtFullName;
    Button btnSignup;
    ImageView imvPicture;

    Student student;
    // Get a non-default Storage bucket
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mapView();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://uit-ocm.appspot.com");

        imvPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup(){
        // Get the data from an ImageView as bytes
        final StorageReference childRef = storageRef.child(edtUserName.getEditText().getText().toString()+".png");
        imvPicture.setDrawingCacheEnabled(true);
        imvPicture.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imvPicture.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        final UploadTask uploadTask = childRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getApplicationContext(),"Có lỗi xảy ra",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Có lỗi xảy ra",Toast.LENGTH_SHORT).show();
                            //throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return childRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();

                            //Add database
                            myRef = database.getReference().child("Students").child(edtUserName.getEditText().getText().toString());
                            // set value to the database
                            student = new Student(edtUserName.getEditText().getText().toString(),edtPassWord.getEditText().getText().toString(),edtDayOfBirth.getEditText().getText().toString(),edtClass.getEditText().getText().toString(),downloadUri.toString(),edtFullName.getEditText().getText().toString());
                            myRef.setValue(student);
                            Toast.makeText(getApplicationContext(), "Đăng kí thành công với ID: " + edtUserName.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                            ///
                        } else {
                            // Handle failures
                            // ...
                            Toast.makeText(getApplicationContext(),"Có lỗi xảy ra",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void mapView(){
        edtUserName = findViewById(R.id.edtUserName);
        edtClass = findViewById(R.id.edtClassName);
        edtDayOfBirth = findViewById(R.id.edtBirthDay);
        edtFullName = findViewById(R.id.edtFullName);
        edtPassWord = findViewById(R.id.edtPassWord);
        btnSignup = findViewById(R.id.btnSignup);
        imvPicture = findViewById(R.id.imageView2);
        database = FirebaseDatabase.getInstance();
    }
}
