package com.uit.khaph.uitocm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Comment> listMessage;
    TextView tvAuthorName;
    TextView tvMessage;
    ImageView imvAuthorPicture;
    String userName;
    FirebaseDatabase database;

    public CommentAdapter(Context context,  ArrayList<Comment> listMessage, int layout, String userName){
        this.context = context;
        this.listMessage = listMessage;
        this.userName = userName;
        this.layout = layout;
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


        Comment comment = listMessage.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        //mapping

        tvAuthorName = (TextView)convertView.findViewById(R.id.tv_comment_Creator);
        tvMessage = (TextView)convertView.findViewById(R.id.tv_comment);
        imvAuthorPicture = (ImageView) convertView.findViewById(R.id.imv_comment_CreatorPicture);
        //set value
        tvAuthorName.setText(comment.getAuthorName());
        tvMessage.setText(comment.getMessage());
        //Picasso.get().load(message.getPicture()).into(imvAuthorPicture);

        return convertView;
    }
}
