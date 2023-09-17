package com.example.filmquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity {
    TextView nuevoUsuario, bienvenidoLabel, continuarLabel;
    ImageView signUpImageView;
    TextInputLayout usuarioSignUpTextField, contrasenaTextField;
    MaterialButton inicioSesion;
    TextInputEditText emailEditText, passwordEditText, confirmPasswordEditText, usuarioidTextView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpImageView = findViewById(R.id.signUpImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioSignUpTextField = findViewById(R.id.usuarioSigUpTextField);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        inicioSesion = findViewById(R.id.inicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        usuarioidTextView = findViewById(R.id.usuarioidTextView);

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionBack();
            }
        });

        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public void validate(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String user = usuarioidTextView.getText().toString().trim();

        boolean isUserValid = Validator.validateUsername(user);
        boolean isEmailValid = Validator.validateEmail(email);
        boolean isPassValid = Validator.validatePassword(password);

        if (isUserValid){
            usuarioidTextView.setError(null);
        }else {
            usuarioidTextView.setError("No debe tener numeros ni simbolos");
        }


        if (isEmailValid) {
            emailEditText.setError(null);
        } else {
            emailEditText.setError("Correo invalido");
            return;
        }

        if (!confirmPassword.equals(password)){
            confirmPasswordEditText.setError("Deben ser iguales");
            return;
        }else {
            passwordEditText.setError(null);
        }

        if (isPassValid){
            passwordEditText.setError(null);
        } else {
            passwordEditText.setError("La contraseña debe tener 8 caracteres, una letra mayúscula, una letra minúscula y un dígito.");
            return;
        }
        registrar(email, password);





    }


    public void registrar(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this
                        , new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(SignUpActivity.this, "Fallo en registrarse", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
    }

    @Override
    public void onBackPressed(){
        transitionBack();
    }

    public void transitionBack (){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(signUpImageView, "LogoImageTrans");
        pairs[1] = new Pair<View, String>(bienvenidoLabel, "textTrans");
        pairs[2] = new Pair<View, String>(continuarLabel, "iniciaSesionTrans");
        pairs[3] = new Pair<View, String>(usuarioSignUpTextField, "emailInputTextTrans");
        pairs[4] = new Pair<View, String>(contrasenaTextField, "passwordInputTextTrans");
        pairs[5] = new Pair<View, String>(inicioSesion, "buttomSingTrans");
        pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTrans");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
            finish();
        }
    }
}