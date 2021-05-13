package com.lmo.n1.apptasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        return tasksList.get(i).id;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        return null;
    }

    private class ItemHelper{
        TextView ;
        LinearLayout layout;
    }
}
