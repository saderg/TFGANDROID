package com.example.autoregistros;

import static com.example.autoregistros.conectaAPI.Urls.URL_POST_EMOTION;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.autoregistros.actualizar.Update;
import com.example.autoregistros.databinding.FragmentCalendarBinding;
import com.example.autoregistros.entidades.Emotion;
import com.example.autoregistros.entidades.User;
import com.example.autoregistros.principal.GraphicFragment;
import com.example.autoregistros.registro.CreateUser;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.autoregistros.databinding.ActivityPrincipalBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Principal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPrincipalBinding binding;

    public TextView userNavMenu, emailNavMenu, dayTxt, dateTxt;
    public EditText reasonEditText, graphicEditText;
    public Spinner spinnerEmotions;
    public ImageButton graphicButton;
    AppBarLayout appBarLayout;
    public FloatingActionButton addEmotion;

    public Bundle bundle;

    int id_user, day, month, year;
    String user_name, password, email_address, date_of_birth;
    String emotion_type, emotion_reason;
    Date emotion_date;
    Emotion emotionPost;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    String[] emotion_types = new String[]{"Miedo", "Alegr??a", "Enfado", "Tristeza", "Asco"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Fragmentos
        GraphicFragment graphicFragment = new GraphicFragment();

        // init components
        userNavMenu = findViewById(R.id.userNavHeader);
        emailNavMenu = findViewById(R.id.emailNavHeader);
        appBarLayout = findViewById(R.id.appBarLayout);

        //init components graphic_day
        graphicButton = findViewById(R.id.graphic_button);
        graphicEditText = findViewById(R.id.graphic_edit_text);

        //Recuperar datos del usuario del Main
        getIntent();
        id_user = getIntent().getExtras().getInt("id_user");
        user_name = getIntent().getStringExtra("user_name");
        password = getIntent().getStringExtra("password");
        email_address = getIntent().getStringExtra("email_address");
        date_of_birth = getIntent().getStringExtra("date_of_birth");

        User loginUser = new User(id_user, user_name, password, email_address, date_of_birth);
        Log.i("LOGIN USER EN PPAL.A", loginUser.toString());

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_day, R.id.nav_calendar, R.id.nav_graphic, R.id.nav_resources)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //init components fragment_day
        spinnerEmotions = findViewById(R.id.spinnerDayF);
        dayTxt = findViewById(R.id.dayText);
        dateTxt = findViewById(R.id.dateText);
        reasonEditText = findViewById(R.id.reasonEditText);
        addEmotion = findViewById(R.id.addEmotion);

        //FRAGMENT DAY
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, emotion_types);
        spinnerEmotions.setAdapter(spinnerAdapter);

        Calendar calendario = Calendar.getInstance();
        day = calendario.get(Calendar.DAY_OF_MONTH);
        Log.i("DDAY", String.valueOf(day));
        dayTxt.setText(String.valueOf(day));

        month = calendario.get(Calendar.MONTH);
        Log.i("MONTH", String.valueOf(month + 1));
        year = calendario.get(Calendar.YEAR);
        Log.i("YEAR", String.valueOf(year));
        dateTxt.setText(month + "/" + year);

        addEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emotion_type = spinnerEmotions.getSelectedItem().toString();
                emotion_reason = reasonEditText.getText().toString();
                try {
                    emotion_date = new java.sql.Date(format.parse(year + "-" + (month + 1) + "-" + day + "T00:00:00").getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //creacion Emotion para el post
                emotionPost = new Emotion(id_user, emotion_type, emotion_reason, emotion_date);

                postEmotion(emotionPost);

            }
        });

        bundle = new Bundle();
        bundle.putInt("id_usuario", id_user);
        graphicFragment.setArguments(bundle);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.perfilAjuste){
            Intent intent = new Intent(Principal.this, Update.class);
            intent.putExtra("id_user" , id_user);
            intent.putExtra("user_name", user_name);
            intent.putExtra("password", password);
            intent.putExtra("email_address", email_address);
            intent.putExtra("date_of_birth", date_of_birth);
            startActivity(intent);
        }
        if(id == R.id.cerrarSesion){
            aviso();
        }
        return super.onOptionsItemSelected(item);

    }

    public void aviso(){
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesi??n")
                .setMessage("??Est??s seguro de cerrar sesi??n?")
                .setPositiveButton("S??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Principal.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Principal.this, Principal.class);
                        intent.putExtra("id_user" , id_user);
                        intent.putExtra("user_name", user_name);
                        intent.putExtra("password", password);
                        intent.putExtra("email_address", email_address);
                        intent.putExtra("date_of_birth", date_of_birth);
                        startActivity(intent);
                    }
                })
                .show();
    }

    public void postEmotion(Emotion emotionPost) {
        Log.i("Emotion en metodo:", emotionPost.toString());
        final ProgressDialog loading = new ProgressDialog(Principal.this);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String URL = URL_POST_EMOTION;
        JSONObject jsonEmotion = new JSONObject();
        try {
            //metemos los valores en el json
            jsonEmotion.put("id_usuario",emotionPost.getId_usuario());
            jsonEmotion.put("emotion_type", emotionPost.getEmotion_type());
            jsonEmotion.put("emotion_reason", emotionPost.getEmotion_reason());
            jsonEmotion.put("emotion_date", emotionPost.getEmotion_date());

        } catch (JSONException e) {
            Log.e("JSON Exception", "JSON Exception: " + e.getMessage());
        }
        // en la request metemos un json en vez de null
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL , jsonEmotion,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Principal.this,"String Response : "+ response.toString(),Toast.LENGTH_LONG).show();
                        try {
                            Log.d("JSON", String.valueOf(response));
                            loading.dismiss();

                            JSONObject body = response.getJSONObject("body");

                        } catch (JSONException e) {
                            System.err.println("Error con el JSON " + e.getMessage());
                            loading.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error on ErrorResponse", "Error: " + error.getMessage());
                Toast.makeText(Principal.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
