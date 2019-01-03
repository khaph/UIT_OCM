package com.uit.khaph.uitocm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Message> listMessage;
    TextView tvAuthorName;
    TextView tvMessage;
    ImageView imvAuthorPicture;

    public MessageAdapter(Context context, int layout, ArrayList<Message> listMessage){
        this.context = context;
        this.layout = layout;
        this.listMessage = listMessage;
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        //mapping
        tvAuthorName = (TextView)convertView.findViewById(R.id.tvAuthorName);
        tvMessage = (TextView)convertView.findViewById(R.id.tvMessage);
        imvAuthorPicture = (ImageView) convertView.findViewById(R.id.imvUserPicture);
        //set value
        Message message = listMessage.get(position);
        tvAuthorName.setText(message.getAuthorName());
        tvMessage.setText(message.getMessage());
        //Picasso.get().load(message.getPicture()).into(imvAuthorPicture);

        return convertView;
    }
}
