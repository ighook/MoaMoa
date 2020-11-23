package com.moamoa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {

    Context context;
    private ArrayList<Menu> arrayList;

    String name;
    String menu;
    String price;

    public MenuAdapter(Context context, ArrayList<Menu> arrayList) {
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
            convertView = inflater.inflate(R.layout.menu_list, null);
        }

        name = arrayList.get(position).getName();

        TextView name_text = (TextView)convertView.findViewById(R.id.food_name);
        menu = arrayList.get(position).getMenu();
        name_text.setText(menu);

        TextView price_text = (TextView)convertView.findViewById(R.id.food_price);
        price = arrayList.get(position).getPrice();
        price_text.setText(price);

        ImageView food_image = convertView.findViewById(R.id.food_image);
        String url = "http://ighook.cafe24.com/moamoa/food/" + name + "/" + arrayList.get(position).getMenu()+".jpg";
        Glide.with(convertView).load(url).error(R.drawable.cutlery).into(food_image);

        return convertView;
    }
}
