package com.example.colormemory.data;

/**
 * Created by james on 30/7/16.
 * All Right Reserved
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.colormemory.R;

import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jpardogo on 22/03/2014.
 */
public class ListDataAdapter extends ArrayAdapter<ScoreObject> {
    private Context mContext;
    private List<ScoreObject> mItems;
    private Random mRandomizer = new Random();

    public ListDataAdapter(Context context, List<ScoreObject> items) {
        super(context, 0, items);
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public ScoreObject getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);

        } else {
            //reserve for reused logic
        }
        TextView numberTextview = (TextView) convertView.findViewById(R.id.number);
        numberTextview.setText(Integer.toString(position));
        TextView scoreTextview = (TextView) convertView.findViewById(R.id.scoreTextview);
        scoreTextview.setText(Integer.toString(getItem(position).score));
        TextView nameTextview = (TextView) convertView.findViewById(R.id.name);
        nameTextview.setText(getItem(position).name);

        int color = Color.argb(255, mRandomizer.nextInt(128)+128, mRandomizer.nextInt(128)+128, mRandomizer.nextInt(128)+128);
        convertView.setBackgroundColor(color);
        return convertView;
    }

}