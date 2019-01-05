package com.uit.khaph.uitocm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentClassMeeting extends Fragment {
    private View vView;
    Button btnAddNew;
    Intent createClassAcitivity;
    String userName;
    FirebaseDatabase database;
    String className;
    String pictureUrl;

    ListView lvMeeting;
    ArrayList<Meeting> listMeeting;
    MeetingAdapter meetingAdapter;
    Meeting meeting;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle){
        vView = inflater.inflate(R.layout.fragment_class_meeting,container,false);
        setHasOptionsMenu(true);
        mapView();

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createClassAcitivity.putExtra("userName",userName);
                createClassAcitivity.putExtra("className",className);
                startActivity(createClassAcitivity);
            }
        });
        getData();

//        Meeting a = new Meeting("phamkha","cuoc hop","hop lop dinh ki","1/1/1997","KHMT2016");
//        listMeeting.add(a);
//        listMeeting.add(a);

        //set listview onItemClick
        lvMeeting.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                meeting = listMeeting.get(position);
                return false;
            }
        });

        lvMeeting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                meeting = listMeeting.get(position);
                Intent intent = new Intent(getContext(),MeetingActivity.class);
                intent.putExtra("userName",userName);
                intent.putExtra("className",className);
                intent.putExtra("meetingName",meeting.getMeetingName());
                intent.putExtra("pictureUrl",pictureUrl);
                intent.putExtra("isEnd",meeting.getIsEnd());
                startActivity(intent);
            }
        });
        registerForContextMenu(lvMeeting);



        return vView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.meeting_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.meetingMenuStart:{
                DatabaseReference newRef = database.getReference().child("Meetings").child(meeting.getClassName()).child(meeting.getMeetingName()).child("isEnd");
                newRef.setValue("0");
                break;
            }
            case R.id.meetingMenuStop:{
                DatabaseReference newRef = database.getReference().child("Meetings").child(meeting.getClassName()).child(meeting.getMeetingName()).child("isEnd");
                newRef.setValue("1");
                break;
            }
            case R.id.meetingMenuDelete:{
                DatabaseReference newRef = database.getReference().child("Meetings").child(meeting.getClassName()).child(meeting.getMeetingName());
                newRef.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(getContext(),"Đã xóa",Toast.LENGTH_SHORT).show();
                        listMeeting.remove(meeting);
                    }
                });
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                intent.putExtra("userName",userName);
//                intent.putExtra("className",className);
//                startActivity(intent);

                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    public void getData(){
        DatabaseReference newRef = database.getReference().child("Meetings");
        // Read from the database
        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMeeting.clear();
                if (dataSnapshot.hasChild(className)){
                    for (DataSnapshot data : dataSnapshot.child(className).getChildren()){
                        listMeeting.add(data.getValue(Meeting.class));
                    }

                    meetingAdapter = new MeetingAdapter(getContext(),R.layout.meeting_line,listMeeting);
                    lvMeeting.setAdapter(meetingAdapter);
                }
                else{
                    if (listMeeting.size()==0)
                        listMeeting.add(new Meeting("","","Không có cuộc họp nào","",className,"3"));
                    meetingAdapter = new MeetingAdapter(getContext(),R.layout.meeting_line,listMeeting);
                    lvMeeting.setAdapter(meetingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void mapView(){
        btnAddNew = vView.findViewById(R.id.btnAddNewClass);
        createClassAcitivity = new Intent(getActivity(),CreateClassActivity.class);
        Bundle classMeetingBundle = getArguments();
        userName = classMeetingBundle.getString("userName","unidentify");
        className = classMeetingBundle.getString("className","unidentify");
        lvMeeting = (ListView)vView.findViewById(R.id.lvListMeeting);
        database = FirebaseDatabase.getInstance();
        listMeeting = new ArrayList<Meeting>();
        pictureUrl = classMeetingBundle.getString("pictureUrl","uni");
    }
}
