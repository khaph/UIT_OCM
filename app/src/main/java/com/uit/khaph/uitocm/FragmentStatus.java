package com.uit.khaph.uitocm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentStatus extends Fragment {
    private View vView;

    ListView lvListStatus;
    EditText edtNewStatus;
    Button btnUpdateNewStatus;
    FirebaseDatabase database;

    String userName;
    String className;
    String status;
    String pictureUrl;

    ArrayList<Status> listStatus;
    StatusAdapter statusAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle){
        vView = inflater.inflate(R.layout.fragment_status,container,false);

        mapView();

        getData();

        btnUpdateNewStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewStatus();
            }
        });


        return vView;
    }

    public void addNewStatus(){
        DatabaseReference newRef = database.getReference().child("Status");
        Status status = new Status(userName, edtNewStatus.getText().toString(),"01/01/2019",className);
        newRef.push().setValue(status);
    }

    public void getData(){
        DatabaseReference newRef = database.getReference().child("Status");
        // Read from the database
        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listStatus.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    listStatus.add(data.getValue(Status.class));
                }
                statusAdapter = new StatusAdapter(getContext(),R.layout.status_line,listStatus);
                lvListStatus.setAdapter(statusAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public void getStatusData(){
//        DatabaseReference newRef = database.getReference().child("Status");
//        // Read from the database
//        newRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChild("Messages")){
//                    listStatus.clear();
//                    for (DataSnapshot data : dataSnapshot.child("Status").getChildren()){
//                        listStatus.add(data.getValue(Status.class));
//                    }
//
//                    messageAdapter = new MessageAdapter(getContext(),R.layout.message_line_left,listMessage);
//                    lvMessage.setAdapter(messageAdapter);
//                }
//                else{
//                    if (listMessage.size()==0)
//                        listMessage.add(new Message("Không có tin nhắn nào","",""));
//                    messageAdapter = new MessageAdapter(getContext(),R.layout.message_line_left,listMessage);
//                    lvMessage.setAdapter(messageAdapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void mapView(){
        database = FirebaseDatabase.getInstance();
        lvListStatus = (ListView)vView.findViewById(R.id.lvListStatus);
        edtNewStatus = (EditText)vView.findViewById(R.id.edtNewStatus);
        btnUpdateNewStatus = (Button)vView.findViewById(R.id.btnAddNewStatus);
        Bundle meetingNowBundle = getArguments();
        userName = meetingNowBundle.getString("userName","uni");
        className = meetingNowBundle.getString("className","uni");
        pictureUrl = meetingNowBundle.getString("pictureUrl","uni");
        listStatus = new ArrayList<Status>();
    }
}
