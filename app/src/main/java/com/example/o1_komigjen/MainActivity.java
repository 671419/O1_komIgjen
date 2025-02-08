package com.example.o1_komigjen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnGallery, btnQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGallery = findViewById(R.id.btnGallery);
        btnQuiz = findViewById(R.id.btnQuiz);

        btnGallery.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, GalleryActivity.class));
        });

        btnQuiz.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, QuizActivity.class));
        });

    }
}