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

public class StatusActivity extends AppCompatActivity {
    TextView tvNotification;
    ListView lvMessage;
    EditText edtNewMessage;
    Button btnSendMessage;
    FirebaseDatabase database;

    String userName;
    String className;
    String status;
    String pictureUrl;

    ArrayList<Comment> listComment;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mapView();
        getData();
        getMessageData();
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewComment();
            }
        });
    }

    public void addNewComment(){
        DatabaseReference newRef = database.getReference().child("Status").child(status).child("Comments");
        Comment comment = new Comment(edtNewMessage.getText().toString(),userName,pictureUrl);
        newRef.push().setValue(comment);
        edtNewMessage.setText("");
    }

    public void getData(){
//        DatabaseReference newRef = database.getReference().child("Status").child(status);
//        // Read from the database
//        newRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChild("className")){
//                    tvNotification.setText(dataSnapshot.getValue().toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        tvNotification.setText("sfjdhsfkjdhsfjkdsf");
    }

    public void getMessageData(){
        DatabaseReference newRef = database.getReference().child("Status").child(status);
        // Read from the database
        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Comments")){
                    listComment.clear();
                    for (DataSnapshot data : dataSnapshot.child("Comments").getChildren()){
                        listComment.add(data.getValue(Comment.class));
                    }

                    commentAdapter = new CommentAdapter(getApplicationContext(),listComment,R.layout.comment_line,userName);
                    lvMessage.setAdapter(commentAdapter);
                }
                else{
                    if (listComment.size()==0)
                        listComment.add(new Comment("Không có comment nào","",""));
                    commentAdapter = new CommentAdapter(getApplicationContext(),listComment,R.layout.comment_line,userName);
                    lvMessage.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void mapView(){
        database = FirebaseDatabase.getInstance();
        tvNotification = (TextView)findViewById(R.id.tvUserStatus);
        lvMessage = (ListView)findViewById(R.id.lvComment);
        edtNewMessage = (EditText)findViewById(R.id.edtNewComment);
        btnSendMessage = (Button)findViewById(R.id.btnSendComment);
        userName = getIntent().getExtras().getString("userName","uni");
        className = getIntent().getExtras().getString("className","uni");
        status = getIntent().getExtras().getString("status","uni");
        pictureUrl = getIntent().getExtras().getString("pictureUrl","uni");
        listComment = new ArrayList<Comment>();
    }
}
