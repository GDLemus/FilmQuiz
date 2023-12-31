package com.example.filmquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    CardView adivinaCardView, creaCardView, onlineCardView;
    ImageView logologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logologin = findViewById(R.id.logologin);
        logologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
                finish();
            }
        });

        adivinaCardView = findViewById(R.id.adivinaCardView);
        adivinaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, AdivinaPelicula.class);
            }
        });

        creaCardView = findViewById(R.id.creaCardView);
        creaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Crea Adivinanzas", Toast.LENGTH_SHORT).show();

            }
        });

        onlineCardView = findViewById(R.id.onlineCardView);
        onlineCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Modo Online", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

}

