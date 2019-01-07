package com.uit.khaph.uitocm;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FragmentUserDetail extends Fragment {
    private View vView;

    String user;
    String pictureUrl;

    TextView tvUserName;
    TextView tvDayOfBirth;
    TextView tvName;
    TextView tvClass;
    ImageView imvUserPicture;
    Button btnLogout;

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle){
        vView = inflater.inflate(R.layout.fragment_user_detail,container,false);

        mapView();

        tvUserName.setText(user);
        getData(user);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        return vView;
    }

    public void mapView(){
        btnLogout = vView.findViewById(R.id.btnLogout);
        imvUserPicture = vView.findViewById(R.id.imvUserPicture);
        tvDayOfBirth = vView.findViewById(R.id.tvDayOfBirth);
        tvName = vView.findViewById(R.id.tvName);
        tvClass = vView.findViewById(R.id.tvClass);
        tvUserName = vView.findViewById(R.id.tvUserName);
        Bundle bun = getArguments();
        if (bun != null){
            user = bun.getString("userName","unidentify");
            pictureUrl = bun.getString("pictureUrl","uni");
        }
        else{
            user = "unidentify";
        }
    }

    private void getData(final String usr){
        DatabaseReference myRef = database.getReference().child("Students").child(usr);
        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("userName")){
                    dataSnapshot.getChildren();
                    tvName.setText(dataSnapshot.child("fullName").getValue().toString());
                    tvDayOfBirth.setText(dataSnapshot.child("dayOfBirth").getValue().toString());
                    tvClass.setText(dataSnapshot.child("className").getValue().toString());
                    Picasso.get().load(pictureUrl).into(imvUserPicture);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
