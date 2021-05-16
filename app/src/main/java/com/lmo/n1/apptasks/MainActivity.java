package com.lmo.n1.apptasks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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
    }

    public void completeTask(Task task){
        TaskDAO.complete(task, MainActivity.this);
        loadTasks();
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