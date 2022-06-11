package com.example.autoregistros;

import static com.example.autoregistros.conectaAPI.Urls.URL_GET_EMOTION_FECHA;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.autoregistros.entidades.Emotion;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Graphics extends AppCompatActivity {

    String emotion_date;
    int id_usuario;
    Date start_date, end_date;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_graficos);

        getIntent();
        emotion_date = getIntent().getStringExtra("date");
        id_usuario = getIntent().getIntExtra("id_usuario", 0);

        try {
            start_date = new java.sql.Date(format.parse(emotion_date + "T00:00:00").getTime());
            end_date = new java.sql.Date(format.parse(emotion_date + "T23:59:59").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("FECHA SELECCIONADA", start_date.toString() + end_date.toString());

        getByRange(id_usuario, start_date, end_date);

        //init compoments graphic_day
        BarChart barChart = (BarChart) findViewById(R.id.barChart);

        //FRAGMENT GRAPHIC
        ArrayList<BarEntry> emociones = new ArrayList<>();
        emociones.add(new BarEntry(0, 3));
        emociones.add(new BarEntry(1, 1));
        emociones.add(new BarEntry(2, 2));
        emociones.add(new BarEntry(3, 1));
        emociones.add(new BarEntry(4, 2));

        BarDataSet barDataSet = new BarDataSet(emociones, "(Miedo, Alegría, Enfado, Tristeza, Asco");
        barDataSet.setValueTextSize(100f);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(18f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
        barChart.animateX(1000);

    }

    public void getByRange(int id_usuario, Date start_date, Date end_date){
        Log.i("START DATE metodo ", start_date.toString());

        ArrayList<Emotion> arrayListEmociones = new ArrayList<>();

        final ProgressDialog loading = new ProgressDialog(Graphics.this);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String url = URL_GET_EMOTION_FECHA + "?user_id=" + id_usuario + "&start_date=" + start_date.toString() + "T00:00:00&end_date=" + end_date.toString() + "T00:00:00";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        StringRequest peticionApi = new StringRequest(Request.Method.GET , url,
                (response -> {
                    Log.i("TAG", "TODO BIEN");

                    try {
                        JSONArray arrayEmociones = new JSONArray(response);

                        for (int i = 0; i < arrayEmociones.length(); i++) {

                            loading.dismiss();

                            Emotion emotion = new Emotion();

                            emotion.setId_emocion(arrayEmociones.getJSONObject(i).getInt("id_emocion"));
                            emotion.setId_usuario(arrayEmociones.getJSONObject(i).getInt("id_usuario"));
                            emotion.setEmotion_type(arrayEmociones.getJSONObject(i).getString("emotion_type"));
                            emotion.setEmotion_reason(arrayEmociones.getJSONObject(i).getString("emotion_reason"));
                            emotion.setEmotion_date(format.parse(arrayEmociones.getJSONObject(i).getString("emotion_date")));
                            System.out.println(emotion.toString());
                            arrayListEmociones.add(emotion);

                            Log.i("emotionsMethod" , emotion.toString());
                        }

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }),
                (error ->{
                    Log.e("error", "Error al pedir los datos." + error.getMessage());
                    loading.dismiss();
                }));
        Volley.newRequestQueue(this).add(peticionApi);

    }
}