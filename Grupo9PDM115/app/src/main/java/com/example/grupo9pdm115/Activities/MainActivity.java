package com.example.grupo9pdm115.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Sesion;

public class MainActivity extends ListActivity  {
    //Variables
    /*TextView tv;
    Calendar mCurrentDate;
    int day, month, year;*/
    ControlBD BDhelper;


    String[] menu = {"Llenar BD", "Ciclo", "Feriado", "Local", "Tipos de local", "Materia", "Unidad", "Grupo", "Tipos de grupo", "Materias del ciclo", "Dias", "Usuario","Horario","Encargado", "Rol","Solicitud","Detalle Reserva"};
    String[] activities = {"Cerrar Sesión", "Ciclo.GestionarCiclo", "Feriado.GestionarFeriado", "Local.GestionarLocal",
            "TipoLocal.GestionarTipoLocal", "Materia.GestionarMateria", "Unidad.GestionarUnidad", "Grupo.GestionarGrupo",
            "TipoGrupo.GestionarTipoGrupo","CicloMateria.GestionarCicloMateria", "Dia.GestionarDia",
            "Usuario.GestionarUsuario","Horario.GestionarHorario","Encargado.GestionarEncargado", "Rol.GestionarRol","Solicitud.GestionarSolicitud","DetalleReserva.GestionarDetalleReserva"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
        BDhelper = new ControlBD(this);


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
        if(positon!= 0){
            String nombreValue = activities[positon];
            try {
                Class<?> clase = Class.forName("com.example.grupo9pdm115.Activities." + nombreValue);
                Intent inte = new Intent(this, clase);
                this.startActivity(inte);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        else {
            /*
            // CODIGO PARA LLENAR BASE DE DATOS
            BDhelper.abrir();
            String tost=BDhelper.llenarBD(this);
            BDhelper.cerrar();*/
            Sesion.setLooggedIn(getApplicationContext(), false);
            Sesion.setNombreUsuario(getApplicationContext(), "");
            Sesion.setAccesoUsuario(getApplicationContext(), null);
            Intent intent = new Intent(this, IniciarSesion.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Esta bandera borra el resto de actividades de la cola
            startActivity(intent);
            String tost = "No hago nada lol";
            Toast.makeText(this, tost, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
