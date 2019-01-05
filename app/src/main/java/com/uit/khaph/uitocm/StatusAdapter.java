package com.uit.khaph.uitocm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StatusAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Status> listStatus;
    TextView tvCreator;
    TextView tvStatus;
    TextView tvDate;

    public StatusAdapter(Context context, int layout, ArrayList<Status> listStatus){
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
        tvCreator = (TextView)convertView.findViewById(R.id.tvCreator);
        tvStatus = (TextView)convertView.findViewById(R.id.tvStatus);
        tvDate = (TextView)convertView.findViewById(R.id.tvCreatedDate);
        //set value
        Status status = listStatus.get(position);
        tvCreator.setText(status.getCreator());
        tvStatus.setText(status.getStatus());
        tvDate.setText(status.getDate());

        return convertView;
    }
}
