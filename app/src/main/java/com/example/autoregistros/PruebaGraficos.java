package com.example.autoregistros;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class PruebaGraficos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_graficos);

        //Utils.init(this);

        //init compoments graphic_day
        BarChart barChart = (BarChart) findViewById(R.id.barChart);

        //FRAGMENT GRAPHIC
        ArrayList<BarEntry> emociones = new ArrayList<>();
        emociones.add(new BarEntry(0, 3));
        emociones.add(new BarEntry(1, 0));
        emociones.add(new BarEntry(2, 2));
        emociones.add(new BarEntry(3, 1));
        emociones.add(new BarEntry(4, 0));
        emociones.add(new BarEntry(5, 4));

        BarDataSet barDataSet = new BarDataSet(emociones, "(Miedo, Alegría, Enfado, Tristeza, Asco, Sorpresa");
        barDataSet.setValueTextSize(100f);
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet.setValueTextColor(Color.CYAN);
        barDataSet.setValueTextSize(18f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
        barChart.animateX(1000);

    }
}