package com.example.grupo9pdm115.Activities.DetalleReserva;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.DetalleReserva;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.Reserva;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Solicitud;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.DiaSpinner;
import com.example.grupo9pdm115.Spinners.EventoEspecialSpinner;
import com.example.grupo9pdm115.Spinners.GrupoSpinner;
import com.example.grupo9pdm115.Spinners.HorarioSpinner;
import com.example.grupo9pdm115.Spinners.TipoGrupoSpinner;
import com.example.grupo9pdm115.Utilidades.FechasHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NuevoDetalleReserva extends AppCompatActivity implements View.OnClickListener{

    Spinner spinnerDia, spinnerHora, spinnerTipoGrupo;
    EditText edtCupo, edtFechaInicio, edtFechaFin, edtLocal, edtNumeroGrupo, edtCodMateria;
    CheckBox chkPeriodoReserva;
    LinearLayout layoutFinReserva, layoutInicioReserva;
    ControlBD helper;
    DiaSpinner diaSpinnerAdapter;
    HorarioSpinner horarioSpinnerAdapter;
    TipoGrupoSpinner tipoGrupoSpinnerAdapter;
    Ciclo cicloActual;
    List<Integer> listaIdsDetalles;
    Solicitud solicitud;
    int idSolicitud = 0, idTipoLocal = 0;
    boolean revision;
    private int dia, mes, anio;

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
        setContentView(R.layout.activity_nuevo_detalle_reserva);

        if(getIntent().getExtras() != null){
            idSolicitud = getIntent().getIntExtra("idSolicitud", 0);
            revision = getIntent().getBooleanExtra("revision", false);
            idTipoLocal = getIntent().getIntExtra("idTipoLocal", 0);
            solicitud = new Solicitud();
            solicitud.consultar(this, String.valueOf(idSolicitud));
        }

        helper = new ControlBD(this);
        listaIdsDetalles = new ArrayList<Integer>();

        edtCupo = (EditText) findViewById(R.id.editCupo);
        edtFechaInicio = (EditText) findViewById(R.id.editInicioReserva);
        edtFechaFin = (EditText) findViewById(R.id.editFinReserva);
        edtLocal = (EditText) findViewById(R.id.editLocalDetalleRNuevo);
        edtNumeroGrupo = (EditText) findViewById(R.id.editNumeroGrupoDetalleRNuevo);
        edtCodMateria = (EditText) findViewById(R.id.editCodMateriaDetalleRNuevo);
        spinnerDia = (Spinner) findViewById(R.id.spinnerDiaDetalleRNuevo);
        spinnerHora = (Spinner) findViewById(R.id.spinnerHoraInicioDetalleRNuevo);
        spinnerTipoGrupo = (Spinner) findViewById(R.id.spinnerTipoGrupoDetalleRNuevo);

        chkPeriodoReserva = (CheckBox) findViewById(R.id.chkPeriodoReserva);
        layoutInicioReserva = (LinearLayout) findViewById(R.id.layoutInicioReservaDetalleRN);
        layoutFinReserva = (LinearLayout) findViewById(R.id.layoutFinReservaDetalleRN);

        helper.abrir();
        diaSpinnerAdapter = new DiaSpinner(helper);
        horarioSpinnerAdapter = new HorarioSpinner(helper);
        tipoGrupoSpinnerAdapter = new TipoGrupoSpinner(this);
        helper.cerrar();


        spinnerDia.setAdapter(diaSpinnerAdapter.getAdapterDia(this));
        spinnerHora.setAdapter(horarioSpinnerAdapter.getAdapterHorario(this));
        spinnerTipoGrupo.setAdapter(tipoGrupoSpinnerAdapter.getAdapterTipoGrupo(this));


        edtFechaInicio.setOnClickListener(this);
        edtFechaFin.setOnClickListener(this);

        chkPeriodoReserva.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    layoutInicioReserva.setVisibility(View.GONE);
                    layoutFinReserva.setVisibility(View.GONE);
                }else{
                    layoutInicioReserva.setVisibility(View.VISIBLE);
                    layoutFinReserva.setVisibility(View.VISIBLE);
                }
            }
        });

        cicloActual = new Ciclo();
        cicloActual = getCicloActual();

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

    public void btnLimpiarTextoNDetalleReserva(View v){
        edtCupo.setText("");
        edtFechaInicio.setText("");
        edtFechaFin.setText("");
        edtLocal.setText("");
        edtCodMateria.setText("");
        edtNumeroGrupo.setText("");
        spinnerDia.setSelection(0);
        spinnerHora.setSelection(0);
        spinnerTipoGrupo.setSelection(0);
    }

    public void btnAgregarNDetalleReserva(View v){
        DetalleReserva detalleReserva = new DetalleReserva();
        int posDia, posHora, posTipoGrupo, idGrupo = 0;
        posDia = spinnerDia.getSelectedItemPosition();
        posHora = spinnerHora.getSelectedItemPosition();
        posTipoGrupo = spinnerTipoGrupo.getSelectedItemPosition();
        if(posDia == 0){
            Toast.makeText(this, "No ha seleccionado un día", Toast.LENGTH_SHORT).show();
            return;
        }
        if(posHora == 0){
            Toast.makeText(this, "No ha seleccionado una hora", Toast.LENGTH_SHORT).show();
            return;
        }

        if (posTipoGrupo != 0){
            idGrupo = getIdGrupoDetalle(tipoGrupoSpinnerAdapter.getIdTipoGrupo(posTipoGrupo));
        }
        else{
            Toast.makeText(this, "No ha seleccionado el grupo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (idGrupo != 0)
            detalleReserva.setIdGrupo(idGrupo);
        else{
            Toast.makeText(this, "Grupo no existe", Toast.LENGTH_SHORT).show();
            return;
        }

        if(chkPeriodoReserva.isChecked()){
            detalleReserva.setInicioPeriodoReserva(cicloActual.getInicioPeriodoClase());
            detalleReserva.setFinPeriodoReserva(cicloActual.getFinPeriodoClase());
        }else{
            String fechaInicio = FechasHelper.cambiarFormatoLocalAIso(edtFechaInicio.getText().toString());
            String fechaFin = FechasHelper.cambiarFormatoLocalAIso(edtFechaFin.getText().toString());
            detalleReserva.setInicioPeriodoReserva(fechaInicio);
            detalleReserva.setFinPeriodoReserva(fechaFin);
        }

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
            Toast.makeText(this, "No existe el local o no pertenece a la categoría de tipo de local a reservar", Toast.LENGTH_SHORT).show();
            return;
        }
        if(local == -1)
            detalleReserva.setIdLocal(0);
        else
            detalleReserva.setIdLocal(local);
        //detalleReserva.setIdLocal(0);
        detalleReserva.setIdEventoEspecial(0);
        detalleReserva.setIdDia(diaSpinnerAdapter.getIdDia(posDia));
        detalleReserva.setCupo(Integer.valueOf(edtCupo.getText().toString()));
        detalleReserva.setAprobado(false);
        detalleReserva.setEstadoReserva(true);
        String res = "";

        detalleReserva.setIdHora(horarioSpinnerAdapter.getIdHorario(posHora));
        boolean seguir = false;
        if(!chkPeriodoReserva.isChecked())
            seguir = detalleReserva.validar(this, 2, detalleReserva);
        if(!seguir && !chkPeriodoReserva.isChecked()){
            Toast.makeText(this, "La fecha de reserva se encuentra en un feriado con reservas bloqueadas", Toast.LENGTH_SHORT).show();
            return;
        }

        //int resp = detalleReserva.validarInt(this, 3, detalleReserva);
        if(!detalleReserva.validar(this, 3, detalleReserva)){
            Toast.makeText(this, "Ya existe un registro para ese grupo y día", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, String.valueOf(resp), Toast.LENGTH_SHORT).show();
            return;
        }

        res = detalleReserva.guardar(getApplicationContext());

        //listaIdsDetalles.add(detalleReserva.getLast(this));
        agregarReserva(idSolicitud, detalleReserva.getLast(getApplication()));

        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    public Ciclo getCicloActual(){
        ahora();
        Ciclo ciclo = new Ciclo();
        String sqlCiclo = "SELECT * from CICLO WHERE '" + String.format("%d-%02d-%02d", anio, mes + 1, dia) + "' BETWEEN INICIO AND FIN";
        helper.abrir();
        Cursor c3 = helper.consultar(sqlCiclo);
        if(c3.moveToFirst()){
            ciclo.setIdCiclo(c3.getInt(0));
            ciclo.setInicioPeriodoClase(c3.getString(5));
            ciclo.setFinPeriodoClase(c3.getString(6));
        }
        helper.cerrar();
        return ciclo;
    }

    public int getIdGrupoDetalle(int idTipoGrupo){
        int grupo = 0;
        String sqlGrupo = "SELECT g.IDGRUPO FROM GRUPO g, CICLOMATERIA cm, MATERIA m, TIPOGRUPO tg \n" +
                "WHERE g.IDCICLOMATERIA = cm.IDCICLOMATERIA AND m.CODMATERIA = cm.CODMATERIA AND g.IDTIPOGRUPO = tg.IDTIPOGRUPO\n" +
                "AND m.CODMATERIA = '"+ edtCodMateria.getText().toString().trim() +"'\n" +
                "AND cm.IDCICLO = " + cicloActual.getIdCiclo() +"\n" +
                "AND g.NUMERO = "+ Integer.valueOf(edtNumeroGrupo.getText().toString().trim()) +"\n" +
                "AND g.IDTIPOGRUPO = "+ idTipoGrupo +";";
        helper.abrir();
        Cursor cursor = helper.consultar(sqlGrupo);
        if(cursor.moveToFirst()){
            grupo = cursor.getInt(0);
        }
        return grupo;
    }

    public int getIdlocal(){
        int idLocal = 0;
        if (edtLocal.getText().toString().trim().equals(""))
            return -1;
        String sqlLocal = "SELECT * FROM LOCAL WHERE NOMBRELOCAL = '" + edtLocal.getText().toString().trim() + "' AND IDTIPOLOCAL =" + idTipoLocal;
        helper.abrir();
        Cursor c2 = helper.consultar(sqlLocal);
        Local local = new Local();
        if(c2.moveToFirst()){
            idLocal = c2.getInt(0);
        }
        helper.cerrar();
        return idLocal;
    }

    public void agregarReserva(int solicitud, int detalleReserva){
        Reserva reserva = new Reserva();
        reserva.setIdDetalleResera(detalleReserva);
        reserva.setIdSolicitud(solicitud);
        reserva.guardar(getApplication());
    }

    /*public void btnBackSolicitud(View v){
        int[] arregloIds = new int[listaIdsDetalles.size()];
        for(int i = 0; i < arregloIds.length; i++){
            arregloIds[i] = listaIdsDetalles.get(i);
        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra("ids", arregloIds);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }*/

}
