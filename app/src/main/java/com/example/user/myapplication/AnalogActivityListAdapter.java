package com.example.user.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by USER on 2017-08-24.
 */

public class AnalogActivityListAdapter extends BaseAdapter {
    private ArrayList<AnalogActivityItem> aList = new ArrayList<AnalogActivityItem>();

    @Override
    public int getCount() {
        return aList.size();
    }

    @Override
    public Object getItem(int position) {
        return aList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());

        return convertView;
    }

    public void addItem(String analogID, int value){
        AnalogActivityItem item = new AnalogActivityItem();

        item.setAnalogID(analogID);
        item.setValue(value);

        aList.add(item);
    }
}
