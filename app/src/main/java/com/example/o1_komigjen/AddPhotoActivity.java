package com.example.o1_komigjen;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AddPhotoActivity extends AppCompatActivity {
private ActivityResultLauncher<String> mGetContent;
    private ImageView imgPhotoToBeAdded;
    private EditText inpName;
    private Button btnSubmit;
    private Uri imageUri;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        imgPhotoToBeAdded = findViewById(R.id.imgPhotoToBeAdded);
        inpName = findViewById(R.id.inpName);
        btnSubmit = findViewById(R.id.btnSubmit);
        DB = new DBHelper(this);



        //register AcResLaun
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    //handle returned uri
                    if (uri != null) {
                            imgPhotoToBeAdded.setImageURI(uri);
                            imageUri = uri;
                            persistUriPermission(uri);
                    }
                });

        mGetContent.launch("image/*");

        imgPhotoToBeAdded.setOnClickListener(v -> {
            mGetContent.launch("image/*");
        });

        btnSubmit.setOnClickListener(v -> {
            if (imageUri != null && !inpName.getText().toString().isEmpty()) {
                DB.insertUserData(inpName.getText().toString().toLowerCase(), imageUri);
                startActivity(new Intent(AddPhotoActivity.this, GalleryActivity.class));
                finish();
            }
        });
    }
    private void persistUriPermission(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }
}