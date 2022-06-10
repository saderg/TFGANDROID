package com.example.autoregistros;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.autoregistros.actualizar.Update;
import com.example.autoregistros.conectaAPI.Methods;
import com.example.autoregistros.registro.CreateUser;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.autoregistros.databinding.ActivityPrincipalBinding;

public class Principal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPrincipalBinding binding;

    public TextView userNavMenu;
    public TextView emailNavMenu;
    AppBarLayout appBarLayout;

    int id_user;
    String user_name;
    String password;
    String email_address;
    String date_of_birth;


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
}
