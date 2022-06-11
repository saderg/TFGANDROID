package com.example.autoregistros.principal;

import static com.example.autoregistros.conectaAPI.Urls.URL_POST_EMOTION;
import static com.example.autoregistros.conectaAPI.Urls.URL_POST_USER;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.autoregistros.MainActivity;
import com.example.autoregistros.R;
import com.example.autoregistros.actualizar.Update;
import com.example.autoregistros.databinding.ActivityPrincipalBinding;
import com.example.autoregistros.entidades.Emotion;
import com.example.autoregistros.entidades.User;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Principal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPrincipalBinding binding;

    public TextView userNavMenu, emailNavMenu, dayTxt, dateTxt;
    public Spinner spinnerEmotions;
    AppBarLayout appBarLayout;
    FloatingActionButton addEmotion;

    public EditText reason_typeEdit = findViewById(R.id.reasonEditText);

    int id_user, day, month, year;
    String user_name, password, email_address, date_of_birth, emotion_type, emotion_reason, emotion_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userNavMenu = findViewById(R.id.userNavHeader);
        emailNavMenu = findViewById(R.id.emailNavHeader);

        appBarLayout = findViewById(R.id.appBarLayout);

        getIntent();
        id_user = getIntent().getExtras().getInt("id");
        user_name = getIntent().getStringExtra("userName");
        password = getIntent().getStringExtra("password");
        email_address = getIntent().getStringExtra("email");
        date_of_birth = getIntent().getStringExtra("date_of_birth");

        System.out.println(id_user);
        System.out.println(user_name);
        System.out.println(password);
        System.out.println(email_address);
        System.out.println(date_of_birth);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_day, R.id.nav_calendar, R.id.nav_graphic, R.id.nav_resources)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        spinnerEmotion();

        dayTxt = findViewById(R.id.dayText);
        dateTxt = findViewById(R.id.dateText);

        Calendar calendario = Calendar.getInstance();
        day = calendario.get(Calendar.DAY_OF_MONTH);
        Log.i("DAY", String.valueOf(day));
        dayTxt.setText(String.valueOf(day));

        month = calendario.get(Calendar.MONTH);
        Log.i("MONTH", String.valueOf(month + 1));
        year = calendario.get(Calendar.YEAR);
        Log.i("YEAR", String.valueOf(year));


        emotion_type = spinnerEmotions.getSelectedItem().toString();
        emotion_reason = reason_typeEdit.getText().toString();
        emotion_date = dayTxt + "-" + month + "-" + day;

        addEmotion = findViewById(R.id.addEmotion);

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
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro de cerrar sesión?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
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

    public void spinnerEmotion(){
        String[] emotion_types = new String[]{"Miedo", "Alegría", "Enfado", "Tristeza", "Asco", "Sorpresa"};
        spinnerEmotions = findViewById(R.id.spinnerDayF);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, emotion_types);
        spinnerEmotions.setAdapter(spinnerAdapter);
    }

    public void postEmotion(Emotion emotion, Context context) {

        System.out.println();
        final ProgressDialog loading = new ProgressDialog(context);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        Log.i("emotion", emotion.toString());
        String URL = URL_POST_EMOTION;
        JSONObject jsonUser = new JSONObject();
        try {
            //metemos los valores en el json
            jsonUser.put("id_usuario", emotion.getId_usuario());
            jsonUser.put("emotion_type",emotion.getEmotion_type());
            jsonUser.put("emotion_reason",emotion.getEmotion_reason());
            jsonUser.put("emotion_date", emotion.getEmotion_date());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // en la request metemos un json en vez de null
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL , jsonUser,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        aviso();
                        try {
                            Log.d("JSON", String.valueOf(response));
                            loading.dismiss();

                            JSONObject body = response.getJSONObject("body");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(context,"Ya existe esta emoción",Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
}
