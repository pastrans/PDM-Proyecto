package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends ListActivity  {
    //Variables
    /*TextView tv;
    Calendar mCurrentDate;
    int day, month, year;*/

    String[] menu = {"Ciclo", "Feriado", "Local", "Tipos de local", "Materia", "Unidad", "Grupo", "Tipos de grupo", "Materias del ciclo"};
    String[] activities = {"GestionarCiclo", "GestionarFeriado", "GestionarLocal", "GestionarTipoLocal", "GestionarMateria", "GestionarUnidad", "GestionarGrupo","GestionarTipoGrupo","GestionarCicloMateria"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));


        //setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_gestionar_ciclo);
        //setContentView(R.layout.activity_nuevo_ciclo);
        //setContentView(R.layout.activity_editar_ciclo);


        /*tv=(TextView) findViewById(R.id.fechaInicio);

        mCurrentDate= Calendar.getInstance();

        day= mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month= mCurrentDate.get(Calendar.MONTH);
        year= mCurrentDate.get(Calendar.YEAR);

        month= month+1;
        tv.setText(day+"/"+month+"/"+year);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear= monthOfYear+1;
                        tv.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });*/

    }

    @Override
    protected void onListItemClick(ListView l, View v, int positon, long id){
        super.onListItemClick(l, v, positon, id);
        String nombreValue = activities[positon];
        try {
            Class<?> clase = Class.forName("com.example.grupo9pdm115." + nombreValue);
            Intent inte = new Intent(this, clase);
            this.startActivity(inte);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
