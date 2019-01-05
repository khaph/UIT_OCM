package com.uit.khaph.uitocm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Message> listMessage;
    TextView tvAuthorName;
    TextView tvMessage;
    ImageView imvAuthorPicture;
    String userName;
    FirebaseDatabase database;

    public MessageAdapter(Context context,  ArrayList<Message> listMessage, String userName){
        this.context = context;
        this.listMessage = listMessage;
        this.userName = userName;
    }

    @Override
    public int getCount() {
        return listMessage.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Message message = listMessage.get(position);
//        if (message.getAuthorName() == this.userName) this.layout = R.layout.message_line_right;
//        else if (message.getAuthorName() != this.userName) this.layout = R.layout.message_line_left;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        //mapping
        if (this.layout == R.layout.message_line_left){
            tvAuthorName = (TextView)convertView.findViewById(R.id.tvAuthorName_left);
            tvMessage = (TextView)convertView.findViewById(R.id.tvMessage_left);
            imvAuthorPicture = (ImageView) convertView.findViewById(R.id.imvUserPicture);
        }if(this.layout == R.layout.message_line_right){
            tvAuthorName = (TextView)convertView.findViewById(R.id.tvAuthorName_right);
            tvMessage = (TextView)convertView.findViewById(R.id.tvMessage_right);
        }
        //set value
        tvAuthorName.setText(message.getAuthorName());
        tvMessage.setText(message.getMessage());
        //Picasso.get().load(message.getPicture()).into(imvAuthorPicture);

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (listMessage.get(position).getAuthorName().equals(userName)){
             layout = R.layout.message_line_right;
        }else
        {
            layout = R.layout.message_line_left;
        }
        return 0;
    }
}
