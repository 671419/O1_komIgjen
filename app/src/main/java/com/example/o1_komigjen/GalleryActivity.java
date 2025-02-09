package com.example.o1_komigjen;

import static android.app.ProgressDialog.show;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity implements RecyclerViewInterface {
    private DBHelper DB;
    private Button btnAddPhoto, btnSort;
    private RecyclerView recyclerView;



    ArrayList<ImageModel> imageModels;
    Image_RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        DB = new DBHelper(this);



        btnAddPhoto = findViewById(R.id.btnAddPhoto);
        btnSort = findViewById(R.id.btnSort);
        recyclerView = findViewById(R.id.recyclerView);
        imageModels = new ArrayList<>();

        setUpImageModels();

        refreshRecycler();

        btnAddPhoto.setOnClickListener(view -> {
            Intent i = new Intent(GalleryActivity.this, AddPhotoActivity.class);
            startActivity(i);
            finish();
        });

        btnSort.setOnClickListener(view -> {
            if (btnSort.getText().toString().equals("Z-A")) {
                btnSort.setText("A-Z");
            } else {
                btnSort.setText("Z-A");
            }
            setUpImageModels();

        });

    }

    private void setUpImageModels() {
        imageModels.clear();
        Cursor cursor = DB.getInOrder();
        if (btnSort.getText().toString().equals("Z-A")) {
            cursor = DB.getInReverseOrder();
        }
        while (cursor.moveToNext()) {
            imageModels.add(new ImageModel(cursor.getInt(0), Uri.parse(cursor.getString(2)), cursor.getString(1)));
        }
        refreshRecycler();
    }


    @Override
    public void onItemClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GalleryActivity.this);
        builder.setMessage("Do you want to delete?");
        builder.setTitle("ObsObs!!");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialog, which) -> {
            DB.deleteFromId(imageModels.get(position).getId());
            setUpImageModels();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        }

        private void refreshRecycler(){
            adapter = new Image_RecyclerViewAdapter(this, imageModels, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }
