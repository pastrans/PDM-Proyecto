package com.example.grupo9pdm115.Activities.DetalleReserva;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.DetalleReserva;
import com.example.grupo9pdm115.Modelos.EventoEspecial;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.Reserva;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.EventoEspecialSpinner;
import com.example.grupo9pdm115.Spinners.HorarioSpinner;
import com.example.grupo9pdm115.Comun.FechasHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NuevoDetalleReservaEspecial extends CyaneaAppCompatActivity implements View.OnClickListener {

    EditText edtLocal, edtCupo, edtFechaReserva;
    Spinner spinnerHoraInicial, spinnerEventoEspecial, spinnerHoraFinal;
    EventoEspecialSpinner eventoEspecialSpinnerAdapter;
    HorarioSpinner horarioSpinnerAdapter;
    ControlBD helper;
    EventoEspecial eventoEspecial;
    int anio, mes, dia, idEventoEspecial, idSolicitud;
    String fechaReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "RSO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_detalle_reserva_especial);

        if(getIntent().getExtras() != null){
            idEventoEspecial = getIntent().getIntExtra("idEventoEspecial", 0);
            idSolicitud = getIntent().getIntExtra("idSolicitud", 0);
            fechaReserva = getIntent().getStringExtra("fechaReserva");
        }

        helper = new ControlBD(this);

        //llenarEventoEspecial();

        edtLocal = (EditText) findViewById(R.id.editLocalDetalleRENuevo);
        edtCupo = (EditText) findViewById(R.id.editCupoDetalleRENuevo);
        edtFechaReserva = (EditText) findViewById(R.id.editFechaDetalleRENuevo);
        edtFechaReserva.setEnabled(false);
        //spinnerEventoEspecial = (Spinner) findViewById(R.id.spinnerEventoDetalleRENuevo);
        spinnerHoraFinal = (Spinner) findViewById(R.id.spinnerHoraFinDetalleRENuevo);
        spinnerHoraInicial = (Spinner) findViewById(R.id.spinnerHoraInicioDetalleRENuevo);

        helper.abrir();
        horarioSpinnerAdapter = new HorarioSpinner(helper);
        //eventoEspecialSpinnerAdapter = new EventoEspecialSpinner(helper);
        helper.cerrar();

        //spinnerEventoEspecial.setAdapter(eventoEspecialSpinnerAdapter.getAdapterEventoEspecial(getApplication()));
        spinnerHoraInicial.setAdapter(horarioSpinnerAdapter.getAdapterHorario(this));
        spinnerHoraFinal.setAdapter(horarioSpinnerAdapter.getAdapterHorario(this));

        edtFechaReserva.setOnClickListener(this);
        edtFechaReserva.setText(FechasHelper.cambiarFormatoIsoALocal(fechaReserva));
    }

    public void btnAgregarNDetalleReservaEspecial(View v) {
        DetalleReserva detalleReserva = new DetalleReserva();
        String res = "", nombreDia = "";
        int posHoraInicial, posHoraFinal, posEventoEspecial, cantidadHorasExtra = 0;
        List<Integer> ids = new ArrayList<Integer>();

        //posEventoEspecial = spinnerEventoEspecial.getSelectedItemPosition();
        posHoraFinal = spinnerHoraFinal.getSelectedItemPosition();
        posHoraInicial = spinnerHoraInicial.getSelectedItemPosition();

        if(edtLocal.getText().toString().equals("")){
            Toast.makeText(this, "Por favor ingrese un local", Toast.LENGTH_SHORT).show();
            return;
        }

        if(edtCupo.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese el cupo de estudiantes", Toast.LENGTH_SHORT).show();
            return;
        }

        int local = getIdlocal();
        if(local == 0){
            Toast.makeText(this, "No existe el local", Toast.LENGTH_SHORT).show();
            return;
        }
        if(local == -1)
            detalleReserva.setIdLocal(0);
        else
            detalleReserva.setIdLocal(local);

        if(posHoraFinal != 0 && posHoraInicial != 0){
            Horario horaInicio = horarioSpinnerAdapter.getHorario(posHoraInicial);
            Horario horaFinal = horarioSpinnerAdapter.getHorario(posHoraFinal);
            String sql = "SELECT idHora FROM HORARIO WHERE HORAINICIO >= '" + horaInicio.getHoraInicio() + "' AND HORAFINAL <= '" + horaFinal.getHoraFinal() + "'";
            Cursor cursor;
            helper.abrir();
            cursor = helper.consultar(sql);
            Log.v("SQL", sql);
            if (cursor.moveToFirst())
                cantidadHorasExtra = cursor.getCount();

            ids.add(cursor.getInt(0));
            while (cursor.moveToNext()){
                ids.add(cursor.getInt(0));
            }
            helper.cerrar();
        }else {
            Toast.makeText(this, "No ha seleccionado la hora del evento", Toast.LENGTH_SHORT).show();
            return;
        }

        /*if (posEventoEspecial != 0){
            eventoEspecial = eventoEspecialSpinnerAdapter.getEventoEspecial(posEventoEspecial);
        }
        else{
            Toast.makeText(this, "No ha seleccionado el evento especial", Toast.LENGTH_SHORT).show();
            return;
        }*/

        try {
            int idDia = getIdDia(getNombreDia(FechasHelper.cambiarFormatoIsoALocal(fechaReserva)));
            detalleReserva.setIdDia(idDia);
            /*Log.v("Dia string: ", getNombreDia(FechasHelper.cambiarFormatoIsoALocal(fechaReserva)));
            Log.v("Dia: ", String.valueOf(idDia));*/
        }catch (ParseException e){
            Toast.makeText(this, "Error con la fecha", Toast.LENGTH_SHORT).show();
            return;
        }
        detalleReserva.setIdEventoEspecial(idEventoEspecial);
        detalleReserva.setIdGrupo(0);
        detalleReserva.setInicioPeriodoReserva(fechaReserva);
        detalleReserva.setFinPeriodoReserva(fechaReserva);
        detalleReserva.setCupo(Integer.valueOf(edtCupo.getText().toString()));
        detalleReserva.setAprobado(false);
        detalleReserva.setEstadoReserva(true);

        if(cantidadHorasExtra == 0){
            detalleReserva.setIdHora(horarioSpinnerAdapter.getIdHorario(posHoraInicial));
            res = detalleReserva.guardar(getApplicationContext());
            agregarReserva(idSolicitud, detalleReserva.getLast(this));
        }
        else{
            for(int k = 0; k < cantidadHorasExtra; k++){
                detalleReserva.setIdHora(ids.get(k));
                res = detalleReserva.guardar(getApplicationContext());
                agregarReserva(idSolicitud, detalleReserva.getLast(this));
            }
        }
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    public int getIdlocal(){
        int idLocal = 0;
        if (edtLocal.getText().toString().trim().equals(""))
            return -1;
        String sqlLocal = "SELECT * FROM LOCAL WHERE NOMBRELOCAL = '" + edtLocal.getText().toString().trim() + "'";
        helper.abrir();
        Cursor c2 = helper.consultar(sqlLocal);
        Local local = new Local();
        if(c2.moveToFirst()){
            idLocal = c2.getInt(0);
        }
        helper.cerrar();
        return idLocal;
    }

    public int getIdDia(String nomDia){
        int id = 0;
        String sqlDia = "SELECT idDia FROM DIA WHERE NOMBREDIA = '" + nomDia + "'";
        helper.abrir();
        Cursor cursorDia = helper.consultar(sqlDia);
        if(cursorDia.moveToFirst()){
            id = cursorDia.getInt(0);
        }
        helper.cerrar();
        return id;
    }

    public String getNombreDia(String fecha) throws ParseException {
        String nomDia = "";
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        nomDia = dateFormat.format(date1);
        switch (nomDia){
            case "Monday":
            case "lunes":
                nomDia = "Lunes";
                break;
            case "Tuesday":
            case "martes":
                nomDia = "Martes";
                break;
            case "miércoles":
            case "Wednesday":
                nomDia = "Miércoles";
                break;
            case "Thursday":
            case "jueves":
                nomDia = "Jueves";
                break;
            case "Friday":
            case "viernes":
                nomDia = "Viernes";
                break;
            case "Saturday":
            case "sábado":
                nomDia = "Sábado";
                break;
            case "Sunday":
            case "domingo":
                nomDia = "Domingo";
                break;
            default:
                nomDia = "";
                break;
        }
        return nomDia;
    }

    public void ahora(){
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);
    }

    @Override
    public void onClick(View v){
        EditText ed = (EditText) v;
        ahora();
        final EditText finalEd = ed;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                finalEd.setText(String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1,  year));
            }
        },anio,mes,dia);

        datePickerDialog.show();
    }

    public void agregarReserva(int solicitud, int detalleReserva){
        Reserva reserva = new Reserva();
        reserva.setIdDetalleResera(detalleReserva);
        reserva.setIdSolicitud(solicitud);
        reserva.guardar(getApplication());
    }

    /*public void llenarEventoEspecial(){
        String sql1 = "INSERT INTO EVENTOESPECIAL(IDCICLOMATERIA, NOMBREEVENTO, FECHAEVENTO, DESCRIPCIONEVENTO) VALUES(1, 'PARCIAL 1', '2020-06-20', 'PARCIAL 1 DE MATEMÁTICAS 1')";
        String sql2 = "INSERT INTO EVENTOESPECIAL(NOMBREEVENTO, FECHAEVENTO, DESCRIPCIONEVENTO) VALUES('CONFERENCIA DE DOCENTES', '2020-06-25', 'CONFERENCIA DE DOCENTES DE LA FIA')";
        helper.abrir();
        helper.getDb().execSQL(sql1);
        helper.getDb().execSQL(sql2);
        helper.cerrar();
    }*/

}
