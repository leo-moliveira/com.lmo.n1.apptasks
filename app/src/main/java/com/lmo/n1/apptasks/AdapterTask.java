package com.lmo.n1.apptasks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
            item.llTaskCompleted = view.findViewById(R.id.llTaskCompleted);
            //item.llTaskCompleted = view.findViewById(R.id.llTaskCompleted);
            item.layout = view.findViewById(R.id.llLayoutList);
            view.setTag(item);
        }else{
            item = (ItemHelper) view.getTag();
        }

        item.llTaskImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,FormActivity.class);
                intent.putExtra("action", "edit");
                intent.putExtra("idTask",tasksList.get(pos).getId());
                context.startActivity(intent);
            }
        });
        item.llTaskTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,FormActivity.class);
                intent.putExtra("action", "edit");
                intent.putExtra("idTask",tasksList.get(pos).getId());
                context.startActivity(intent);
            }
        });
        item.llTaskDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,FormActivity.class);
                intent.putExtra("action", "edit");
                intent.putExtra("idTask",tasksList.get(pos).getId());
                context.startActivity(intent);
            }
        });
        item.llTaskImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((MainActivity) context).deleteTask(tasksList.get(pos));
                return true;
            }
        });
        item.llTaskTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((MainActivity) context).deleteTask(tasksList.get(pos));
                return true;
            }
        });
        item.llTaskDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((MainActivity) context).deleteTask(tasksList.get(pos));
                return true;
            }
        });

        item.llTaskCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).completeTask(tasksList.get(pos));
                if(item.llTaskCompleted.isChecked()){
                    item.llTaskCompleted.setChecked(false);
                }else{
                    item.llTaskCompleted.setChecked(true);
                }
            }
        });



        Task task = tasksList.get(pos);
        if(task.getImage() != null){
            item.llTaskImg.setImageURI(Uri.parse(task.getImage()));
        }
        item.llTaskTitle.setText(task.getTitle());
        item.llTaskDesc.setText(task.getDescription());
        item.llTaskCompleted.setChecked(task.getCompleted());
        if(pos % 2 == 0){
            item.layout.setBackgroundColor(Color.rgb(230,230,230));
        }else{
            item.layout.setBackgroundColor(Color.WHITE);
        }
        return view;
    }

    private void configClick(){

    }

    private class ItemHelper{
        ImageView llTaskImg;
        TextView llTaskTitle;
        TextView llTaskDesc;
        CheckBox llTaskCompleted;
        LinearLayout layout;
    }
}
