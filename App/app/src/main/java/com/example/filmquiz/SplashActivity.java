package com.example.filmquiz;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //Agregar animaciones

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        TextView appTextView = findViewById(R.id.apptextView);
        TextView fitnetTextView = findViewById(R.id.fitnettextView);
        ImageView logoImageView = findViewById(R.id.logoimageView);

        appTextView.setAnimation(animation2);
        fitnetTextView.setAnimation(animation2);
        logoImageView.setAnimation(animation1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(logoImageView, "logoImageTrans");
                    pairs[1] = new Pair<View, String>(fitnetTextView, "textTrans");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                        finish();
                    }
                }
            }
        },4000);


    }
}