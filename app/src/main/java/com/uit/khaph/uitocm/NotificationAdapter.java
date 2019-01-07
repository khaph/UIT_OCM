package com.uit.khaph.uitocm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Notification> listStatus;
    TextView tvCreator;
    TextView tvStatus;
    TextView tvDate;

    public NotificationAdapter(Context context, int layout, ArrayList<Notification> listStatus){
        this.context = context;
        this.layout = layout;
        this.listStatus = listStatus;
    }

    @Override
    public int getCount() {
        return listStatus.size();
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
        tvCreator = (TextView)convertView.findViewById(R.id.tv_notification_Creator);
        tvStatus = (TextView)convertView.findViewById(R.id.tv_notification);
        tvDate = (TextView)convertView.findViewById(R.id.tv_notification_CreatedDate);
        //set value
        Notification notification = listStatus.get(position);
        tvCreator.setText(notification.getCreator());
        tvStatus.setText(notification.getStatus());
        tvDate.setText(notification.getDate());

        return convertView;
    }
}
