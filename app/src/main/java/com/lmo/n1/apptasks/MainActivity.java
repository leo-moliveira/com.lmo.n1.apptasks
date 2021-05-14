package com.lmo.n1.apptasks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvMainTasks;
    private AdapterTask adapterTask;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("action", "new");
                startActivity(intent);
            }
        });

        lvMainTasks = findViewById(R.id.lvMainTasks);
        loadTasks();
        configListView();
    }


    private void configListView(){
        lvMainTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Task selectedTask = taskList.get(pos);
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("action", "edit");
                intent.putExtra("idTask",selectedTask.getId());
                startActivity(intent);
            }
        });
        lvMainTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Task selectedTask = taskList.get(pos);
                deleteTask(selectedTask);
                return true;
            }
        });
    }
    public void deleteTask(Task task){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(android.R.drawable.ic_input_delete);
        alert.setTitle(R.string.alertTitleDelete);
        alert.setMessage(MainActivity.this.getString(R.string.alertMessagePrefix) + " '" + task.getTitle() + "' " + MainActivity.this.getString(R.string.alertMessageSufix) );
        alert.setNeutralButton(R.string.cancel,null);
        alert.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TaskDAO.delete(task.getId(),MainActivity.this);
                loadTasks();
            }
        });
        alert.show();
    }

    public void loadTasks(){
        taskList = TaskDAO.getListOfTasks(this);
        adapterTask = new AdapterTask(this,taskList);
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,taskList);
        lvMainTasks.setAdapter(adapterTask);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}