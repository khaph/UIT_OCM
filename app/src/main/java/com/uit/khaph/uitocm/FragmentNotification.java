package com.uit.khaph.uitocm;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragmentNotification extends Fragment {
    private View vView;

    ListView lvNotification;

    String userName;
    String className;
    String notification;
    String pictureUrl;

    NotificationAdapter notificationAdapter;

    ArrayList<Notification> listNotification;

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle){
        vView = inflater.inflate(R.layout.fragment_notification,container,false);

        mapView();

        getData();


        return vView;
    }

    public void mapView(){
        database = FirebaseDatabase.getInstance();
        lvNotification = (ListView)vView.findViewById(R.id.lvNotification);
        Bundle meetingNowBundle = getArguments();
        userName = meetingNowBundle.getString("userName","uni");
        className = meetingNowBundle.getString("className","uni");
        pictureUrl = meetingNowBundle.getString("pictureUrl","uni");
        listNotification = new ArrayList<Notification>();
    }

    public void getData(){
        DatabaseReference newRef = database.getReference().child("Notifications");
        // Read from the database
        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listNotification.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    listNotification.add(data.getValue(Notification.class));
                }
                notificationAdapter = new NotificationAdapter(getContext(),R.layout.notification_line,listNotification);
                lvNotification.setAdapter(notificationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
