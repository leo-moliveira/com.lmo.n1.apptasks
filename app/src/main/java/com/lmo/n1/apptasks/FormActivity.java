package com.lmo.n1.apptasks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormActivity extends AppCompatActivity {
    private ImageButton formImageButton;
    private EditText formTaskTitle;
    private EditText formTaksDesc;
    private CheckBox formTaskCheckBox;
    private Button formBtnSave;
    private String action;
    private Task task;
    private Bitmap image;
    private String imageUri;
    private int SELECT_PICTURE = 200;
    private int REQUEST_CAMERA = 201;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
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

        formImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        formBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                finish();
            }
        });
    }
    public void selectImage(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.formTitleImageSelect);
        alert.setNeutralButton(R.string.cancel,null);
        alert.setNegativeButton(R.string.formImageFromCamera, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cameraIntent();
            }
        });
        alert.setPositiveButton(R.string.formImageFromGallery, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                galleryIntent();
            }
        });
        alert.show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){

            if(requestCode == SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if(null != selectedImageUri){
                    formImageButton.setImageURI(selectedImageUri);
                    imageUri = selectedImageUri.toString();
                }
            }else if(requestCode == REQUEST_CAMERA){
                if (resultCode == RESULT_OK) {
                    try {
                        image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(imageUri));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                formImageButton.setImageURI(Uri.parse(imageUri));
            }
        }
    }

    public void galleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent,"Select File"),SELECT_PICTURE);

    }

    public void cameraIntent(){
        Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        Log.d("trak",intent.toString());

        if(intent.resolveActivity(getPackageManager()) != null){
            File photo = null;
            try {
                photo = createImageFile();
            }catch (IOException e){
                Log.e("photo",e.toString());
            }
            if(photo != null){
                Uri file = FileProvider.getUriForFile(this,"com.example.fileprovider",photo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,file);
                startActivityForResult(intent,REQUEST_CAMERA);
            }else{
                Log.e("app-err","File not found");
            }
        }
    }

    public File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        imageUri = image.getPath();
        return image;
    }

    public void loadForm(){
        int idTask = getIntent().getIntExtra("idTask",0);
        if(idTask != 0){
            task = TaskDAO.getTaskById(idTask,this);
            if(task.getImage() != null){
                formImageButton.setImageURI(Uri.parse(task.getImage()));
            }
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
            task.setImage(imageUri);
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
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}