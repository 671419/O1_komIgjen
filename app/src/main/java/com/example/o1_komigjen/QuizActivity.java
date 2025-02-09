package com.example.o1_komigjen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static final String PREF_HIGH_SCORE = "high_score";

    Random random = new Random();
    private String correctAnswer;
    private static ArrayList<ImageModel> quizModels;
    private int currentImage;
    private int highScore;
    private int currentScore;
    private Button btnSvar1, btnSvar2, btnSvar3;
    private TextView txtCS, txtHS;
    private ImageView imgView;

    private DBHelper db;
    private int antallBilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        imgView = findViewById(R.id.imgQuizBilde);
        btnSvar1 = findViewById(R.id.btnSvar1);
        btnSvar2 = findViewById(R.id.btnSvar2);
        btnSvar3 = findViewById(R.id.btnSvar3);
        db = new DBHelper(this);
        quizModels = new ArrayList<>();
        txtCS = findViewById(R.id.txtCurrentScore);
        txtHS = findViewById(R.id.txtHighScore);
        currentScore = 0;

        sharedPreferences = getPreferences(MODE_PRIVATE);
        highScore = sharedPreferences.getInt("highScore", 1);
        loadHSFromPreferences();
        txtHS.setText("High Score: " + highScore);




        Cursor cursor = db.getAllData();
        while(cursor.moveToNext()){
            quizModels.add(new ImageModel(cursor.getInt(0), Uri.parse(cursor.getString(2)), cursor.getString(1)));
        }


        antallBilder = quizModels.size();

        currentImage = random.nextInt(antallBilder);

        setUpQuestion(currentImage);

        btnSvar1.setOnClickListener(view -> {
            if (btnSvar1.getText().toString().equals(quizModels.get(currentImage).getName())) {
                currentScore++;
                txtCS.setText("Current Score: " + currentScore);

                currentImage = random.nextInt(antallBilder);
                setUpQuestion(currentImage);
            }
            else {
                if (currentScore > highScore) {
                    highScore = currentScore;
                    saveHSToPreferences();
                }
                Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnSvar2.setOnClickListener(view -> {
            if (btnSvar2.getText().toString().equals(quizModels.get(currentImage).getName())) {
                currentScore++;
                txtCS.setText("Current Score: " + currentScore);

                currentImage = random.nextInt(antallBilder);
                setUpQuestion(currentImage);
            } else {
                if (currentScore > highScore) {
                    highScore = currentScore;
                    saveHSToPreferences();
                }
                Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnSvar3.setOnClickListener(view -> {
            if (btnSvar3.getText().toString().equals(quizModels.get(currentImage).getName())) {
                currentScore++;
                txtCS.setText("Current Score: " + currentScore);

                currentImage = random.nextInt(antallBilder);
                setUpQuestion(currentImage);
            } else {
                if (currentScore > highScore) {
                    highScore = currentScore;
                    saveHSToPreferences();
                }
                Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void setUpQuizModels(){

    }

    private void setUpQuestion(int currentImage) {
        imgView.setImageURI(quizModels.get(currentImage).getUri());
        int correctButton = random.nextInt(3)+1;

        if (correctButton == 1) {
            btnSvar1.setText(quizModels.get(currentImage).getName());
            btnSvar2.setText(quizModels.get((currentImage+1) % antallBilder).getName());
            btnSvar3.setText(quizModels.get((currentImage+2) % antallBilder).getName());
        } else if (correctButton == 2) {
            btnSvar2.setText(quizModels.get(currentImage).getName());
            btnSvar1.setText(quizModels.get((currentImage+1) % antallBilder).getName());
            btnSvar3.setText(quizModels.get((currentImage+2) % antallBilder).getName());
        } else {
            btnSvar3.setText(quizModels.get(currentImage).getName());
            btnSvar2.setText(quizModels.get((currentImage+1) % antallBilder).getName());
            btnSvar1.setText(quizModels.get((currentImage+2) % antallBilder).getName());
        }
    }

    private void saveHSToPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_HIGH_SCORE, highScore);
        editor.apply();
    }

    private void loadHSFromPreferences() {
        highScore = sharedPreferences.getInt(PREF_HIGH_SCORE, 0);
    }
}