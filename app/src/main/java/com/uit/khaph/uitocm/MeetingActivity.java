package com.uit.khaph.uitocm;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MeetingActivity extends AppCompatActivity {

    TextView tvNotification;
    ListView lvMessage;
    EditText edtNewMessage;
    Button btnSendMessage;
    FirebaseDatabase database;

    String userName;
    String className;
    String meetingName;
    String pictureUrl;

    ArrayList<Message> listMessage;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        mapView();

        getData();

        getMessageData();

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMessage();
            }
        });
    }

    public void addNewMessage(){
        DatabaseReference newRef = database.getReference().child("Meetings").child(className).child(meetingName).child("Messages");
        Message message = new Message(edtNewMessage.getText().toString(),userName,pictureUrl);
        newRef.push().setValue(message);
    }

    public void getData(){
        DatabaseReference newRef = database.getReference().child("Meetings").child(className).child(meetingName);
        // Read from the database
        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("information")){
                    tvNotification.setText(dataSnapshot.child("information").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getMessageData(){
        DatabaseReference newRef = database.getReference().child("Meetings").child(className).child(meetingName);
        // Read from the database
        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Messages")){
                    listMessage.clear();
                    for (DataSnapshot data : dataSnapshot.child("Messages").getChildren()){
                        listMessage.add(data.getValue(Message.class));
                    }

                    messageAdapter = new MessageAdapter(getApplicationContext(),R.layout.message_line,listMessage);
                    lvMessage.setAdapter(messageAdapter);
                }
                else{
                    if (listMessage.size()==0)
                        listMessage.add(new Message("Không có tin nhắn nào","",""));
                    messageAdapter = new MessageAdapter(getApplicationContext(),R.layout.message_line,listMessage);
                    lvMessage.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void mapView(){
        database = FirebaseDatabase.getInstance();
        tvNotification = (TextView)findViewById(R.id.tvNotification);
        lvMessage = (ListView)findViewById(R.id.lvMessage);
        edtNewMessage = (EditText)findViewById(R.id.edtNewMessage);
        btnSendMessage = (Button)findViewById(R.id.btnSendMessage);
        userName = getIntent().getExtras().getString("userName","uni");
        className = getIntent().getExtras().getString("className","uni");
        meetingName = getIntent().getExtras().getString("meetingName","uni");
        pictureUrl = getIntent().getExtras().getString("pictureUrl","uni");
        listMessage = new ArrayList<Message>();
    }
}
