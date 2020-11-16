package com.moamoa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends BaseAdapter {

    Context context;
    private ArrayList<Store> arrayList;

    String name;
    String telephone;
    String address;

    public MyAdapter(Context context, ArrayList<Store> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
        }


        TextView name_text = (TextView)convertView.findViewById(R.id.name);
        name = arrayList.get(position).getName();
        name_text.setText(name);

        TextView telephone_text = (TextView)convertView.findViewById(R.id.telephone);
        telephone = arrayList.get(position).getTelephone();
        if(!telephone.equals("null")) {
            telephone_text.setText(telephone);
        } else {
            telephone_text.setText("");
        }

        TextView address_text = (TextView)convertView.findViewById(R.id.address);
        address = arrayList.get(position).getAddress();
        address_text.setText(address);

        ImageView image_view = (ImageView)convertView.findViewById(R.id.imageView1);

        String url = "http://ighook.cafe24.com/moamoa/store_image/"+arrayList.get(position).getName()+".jpg";
        Glide.with(convertView).load(url).error(R.drawable.cutlery).into(image_view);

        return convertView;
    }
}
