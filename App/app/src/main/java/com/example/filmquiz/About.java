package com.example.filmquiz;

import static com.example.filmquiz.MainActivity.redirectActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class About extends AppCompatActivity {


    TextView emailTextView, usuarioidTextView;
    Button logoutButton;


    ImageView exitm, ProfileimageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        exitm = findViewById(R.id.exittoabout);
        exitm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(About.this, MainActivity.class);
            }
        });

        ProfileimageView = findViewById(R.id.ProfileimageView);
        emailTextView = findViewById(R.id.emailTextView);
        usuarioidTextView = findViewById(R.id.usuarioidTextView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            emailTextView.setText(user.getEmail());


            if (user.getPhotoUrl() != null) {

                String photoUrl = user.getPhotoUrl().toString();
                Picasso.get().load(photoUrl).into(ProfileimageView);
            } else {

                ProfileimageView.setImageResource(R.drawable.baseline_person_pin_24);
            }

            String displayName = user.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                usuarioidTextView.setText(displayName);
            } else {

                usuarioidTextView.setText("Nombre de usuario no disponible");
            }

        }




        logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(About.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}