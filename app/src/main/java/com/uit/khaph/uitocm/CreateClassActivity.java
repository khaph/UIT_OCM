package com.uit.khaph.uitocm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateClassActivity extends AppCompatActivity {

    String userName;
    EditText edtMeetingName;
    EditText edtDate;
    EditText edtClassName;
    EditText edtInformation;
    Button btnCreate;
    Meeting meeting;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        this.setFinishOnTouchOutside(true);
        mapView();

        btnCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createMeeting();
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                intent.putExtra("userName",userName);
//                intent.putExtra("className",className);
//                startActivity(intent);
                finish();
            }
        });
    }

    public void createMeeting(){
        myRef = database.getReference().child("Meetings").child(className).child(edtMeetingName.getText().toString());
        // set value to the database
        meeting = new Meeting(userName, edtMeetingName.getText().toString(), edtInformation.getText().toString(), edtDate.getText().toString(), className,"2");
        myRef.setValue(meeting);
        Toast.makeText(getApplicationContext(), "Tạo cuộc họp thành công : " + edtMeetingName.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    public void mapView(){
        userName = getIntent().getExtras().getString("userName","unidentify");
        className = getIntent().getExtras().getString("className","unidentify");
        edtMeetingName = (EditText)findViewById(R.id.edtMeetingName);
        edtDate = (EditText)findViewById(R.id.edtDate);
        edtInformation = (EditText)findViewById(R.id.edtInformation);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        database = FirebaseDatabase.getInstance();
    }
}
