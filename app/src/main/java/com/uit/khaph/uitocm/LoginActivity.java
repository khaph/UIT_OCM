package com.uit.khaph.uitocm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.android.volley.VolleyLog.TAG;

public class LoginActivity extends Activity {

    ImageView ivMainImage;
    Animation aniMainImage, bot_to_top;
    EditText edtUserName;
    EditText edtPassWord;
    TextView tvSignUp;
    TextView textView;
    Button btnSubmit;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Intent main;
    Intent signup;
    RelativeLayout buc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MapView();
        ivMainImage.startAnimation(aniMainImage);
        ShowLoginForm();


        //connect database
        DatabaseReference dbr = database.getReference();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(edtUserName.getText().toString(),edtPassWord.getText().toString());
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signup);
            }
        });
    }

    private void Login(final String usr, final String psw){
        DatabaseReference myRef = database.getReference().child("Students").child(usr);
        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("passWord")){
                    dataSnapshot.getChildren();
                    String value = dataSnapshot.child("passWord").getValue().toString();
                    if (psw.equals(value)){
                        Toast.makeText(getApplicationContext(),"Đã đăng nhập với ID: "+usr,Toast.LENGTH_SHORT).show();
                        main.putExtra("userName",usr);
                        main.putExtra("className",dataSnapshot.child("className").getValue().toString());
                        main.putExtra("pictureUrl",dataSnapshot.child("pictureUrl").getValue().toString());
                        startActivity(main);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Sai Tên đăng nhập hoặc Mật khẩu",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Sai Tên đăng nhập hoặc Mật khẩu",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private void ReadJSON(String url){
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try{
//                            JSONObject jsonObject = response.getJSONObject(0);
//                            edtUserName.setText(jsonObject.getString("UserName"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG);
//                    }
//                }
//        );
//        requestQueue.add(jsonArrayRequest);
//    }

    public void ShowLoginForm(){
        aniMainImage.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                buc.setVisibility(View.VISIBLE);
                buc.startAnimation(bot_to_top);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void MapView(){
        ivMainImage = (ImageView)findViewById(R.id.main_img);
        aniMainImage = AnimationUtils.loadAnimation(this,R.anim.img_main_anim);
        bot_to_top = AnimationUtils.loadAnimation(this,R.anim.bot_to_top);
        edtUserName = (EditText)findViewById(R.id.usr);
        edtPassWord = (EditText)findViewById(R.id.psw);
        tvSignUp = (TextView)findViewById(R.id.tvSignUp);
        btnSubmit = (Button)findViewById(R.id.submit);
        main = new Intent(this,MainActivity.class);
        signup = new Intent(this,SignupActivity.class);
        buc = (RelativeLayout)findViewById(R.id.bucket_layout);
    }
}
