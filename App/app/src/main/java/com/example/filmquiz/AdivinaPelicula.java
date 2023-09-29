package com.example.filmquiz;

import static com.example.filmquiz.MainActivity.redirectActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdivinaPelicula extends AppCompatActivity {

    private static String JSON_URL = "https://run.mocky.io/v3/a05f656d-8cb1-4287-b22d-69e39a4b1464";

    private static final long START_TIME_IN_MILLIS = 60000;


    List<MovieModelClass> movieList;
    private TextView mTextViewCountDown;
    private ImageView mButtonStartPause;
    private ImageView mButtonReset, mlogo;

    private  Button mButtonRespuesta;

    TextView ResultadotextView;
    private ImageView mPeliView;
    private TextInputEditText mPeliEditText;
    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    MovieModelClass currentMovie;

    private Random random;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adivina_pelicula2);

        movieList = new ArrayList<>();
        random = new Random();
        mPeliView = findViewById(R.id.PeliView);
        mPeliEditText = findViewById(R.id.peliEditText);
        mlogo = findViewById(R.id.logomain);

        GetData getData = new GetData();
        getData.execute();

        mlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(AdivinaPelicula.this, MainActivity.class);
            }
        });

        ResultadotextView = findViewById(R.id.ResultadotextView);


        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    // Muestra la primera película al azar al iniciar la actividad
                    mostrarPeliculaAlAzar();

                } else {
                    startTimer();
                    verificarCoincidencia();


                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateCountDownText();
    }


    //URL JSON
    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    e.printStackTrace();
                    current = "Error al obtener datos. Verifica tu conexión a Internet.";
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                current = "Error inesperado.";
            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray("peliculas_accion");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    MovieModelClass model = new MovieModelClass();
                    model.setId(jsonObject1.getString("id"));
                    model.setNombre(jsonObject1.getString("nombre"));
                    model.setImagen(jsonObject1.getString("imagen"));

                    movieList.add(model);
                }

                // Una vez que se hayan cargado los datos, muestra la primera película al azar
                mostrarPeliculaAlAzar();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void mostrarPeliculaAlAzar() {
        if (!movieList.isEmpty()) {
            int randomIndex = random.nextInt(movieList.size());
            currentMovie = movieList.get(randomIndex);

            // Muestra la imagen de la película al azar
            Glide.with(this) // Utiliza 'this' que es el contexto de la actividad MainActivity
                    .load(currentMovie.getImagen())
                    .into(mPeliView);
        }
    }

    private void verificarCoincidencia() {
        String nombreIngresado = mPeliEditText.getText().toString().trim();

        if (!nombreIngresado.isEmpty() && currentMovie != null) {
            // Verificar si el nombre ingresado coincide con el nombre de la película actual
            if (nombreIngresado.equalsIgnoreCase(currentMovie.getNombre())) {
                // Coincide
                ResultadotextView.setText("Correcto");
            } else {
                // No coincide
                ResultadotextView.setText("Incorrecto");
            }

            // Muestra una nueva película al azar después de verificar
            mostrarPeliculaAlAzar();
            mPeliEditText.setText(""); // Limpia el campo de entrada
        } else {
            // El campo de entrada está vacío o no se han cargado datos
            Toast.makeText(AdivinaPelicula.this, "Por favor, ingresa un nombre y espera a que se carguen los datos.", Toast.LENGTH_SHORT).show();
        }
    }











    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
//                mButtonStartPause.setText("Start");
//                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);

                Intent intent = new Intent(AdivinaPelicula.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();

        mTimerRunning = true;
//        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
//        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }



}