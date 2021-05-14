package com.lmo.n1.apptasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {
    private ImageButton formImageButton;
    private EditText formTaskTitle;
    private EditText formTaksDesc;
    private CheckBox formTaskCheckBox;
    private Button formBtnSave;
    private String action;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        formImageButton = findViewById(R.id.formImageButton);
        formTaskTitle = findViewById(R.id.formTaskTitle);
        formTaksDesc = findViewById(R.id.formTaksDesc);
        formTaskCheckBox = findViewById(R.id.formTaskCheckBox);
        formBtnSave = findViewById(R.id.formBtnSave);
        action = getIntent().getStringExtra("action");

        if (action.equals("edit")){
            loadForm();
        }else{}

        formBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    public void loadForm(){
        int idTask = getIntent().getIntExtra("idTask",0);
        if(idTask != 0){
            task = TaskDAO.getTaskById(idTask,this);
            //formImageButton
            formTaskTitle.setText(task.getTitle());
            formTaksDesc.setText(task.getDescription());
            formTaskCheckBox.setChecked(task.getCompleted());
        }
    }

    private void save(){
        if(formTaskTitle.getText().length() == 0  || formTaksDesc.getText().length() == 0){
            Toast.makeText(this,R.string.formEmpty,Toast.LENGTH_LONG).show();
        }else{
            if(action.equals("new")){
                task = new Task();
            }else{}

            task.setTitle(formTaskTitle.getText().toString());
            task.setDescription(formTaksDesc.getText().toString());
            task.setCompleted(formTaskCheckBox.isChecked());
            if (action.equals("edit")){
                TaskDAO.edit(task,this);
            }else{
                TaskDAO.insert(task,this);
                formTaskTitle.setText("");
                formTaksDesc.setText("");
                formTaskCheckBox.setChecked(false);
            }
        }
    }
}