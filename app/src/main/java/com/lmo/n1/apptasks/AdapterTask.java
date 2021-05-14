package com.lmo.n1.apptasks;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AdapterTask extends BaseAdapter {
    private List<Task> tasksList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterTask(Context context, List<Task> tasksList){
        this.tasksList = tasksList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.tasksList.size();
    }

    @Override
    public Object getItem(int i) {
        return tasksList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return tasksList.get(i).getId();
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        ItemHelper item;

        if(view == null){
            view = inflater.inflate(R.layout.layout_list,null);
            item = new ItemHelper();
            item.llTaskImg = view.findViewById(R.id.llTaskImg);
            item.llTaskTitle = view.findViewById(R.id.llTaskTitle);
            item.llTaskDesc = view.findViewById(R.id.llTaskDesc);
            //item.llTaskCompleted = view.findViewById(R.id.llTaskCompleted);
            //item.llTaskCompleted = view.findViewById(R.id.llTaskCompleted);
            item.layout = view.findViewById(R.id.llLayoutList);
            view.setTag(item);
        }else{
            item = (ItemHelper) view.getTag();
        }
        Task task = tasksList.get(pos);
        //item.llTaskImg.(task.getImage());
        item.llTaskTitle.setText(task.getTitle());
        item.llTaskDesc.setText(task.getDescription());
        //item.llTaskCompleted.setChecked(task.getCompleted());
        if(pos % 2 == 0){
            item.layout.setBackgroundColor(Color.rgb(230,230,230));
        }else{
            item.layout.setBackgroundColor(Color.WHITE);
        }
        return view;
    }

    private class ItemHelper{
        ImageView llTaskImg;
        TextView llTaskTitle;
        TextView llTaskDesc;
        CheckedTextView llTaskCompleted;
        LinearLayout layout;
    }
}
