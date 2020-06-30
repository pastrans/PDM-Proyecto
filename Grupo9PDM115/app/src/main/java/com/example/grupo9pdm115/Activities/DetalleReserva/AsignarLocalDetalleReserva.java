package com.example.grupo9pdm115.Activities.DetalleReserva;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.LocalAdapter;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.DetalleReserva;
import com.example.grupo9pdm115.Modelos.EventoEspecial;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

import java.util.List;

public class AsignarLocalDetalleReserva extends CyaneaAppCompatActivity {

    ListView listaLocal;
    LocalAdapter listaLocalAdapter;
    ControlBD helper;
    Local local;
    DetalleReserva detalleReserva;
    TextView txtMateria, txtDiaHora;
    EditText edtLocal;
    int idDetalleReserva, cantidadRelacionados = 0;
    int[] relacionados, intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "GSO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_local_detalle_reserva);
        listaLocal = (ListView) findViewById(R.id.listaLocalesDisponibles);
        helper = new ControlBD(this);
        detalleReserva = new DetalleReserva();
        txtMateria = (TextView) findViewById(R.id.txtMateriaGAsignarL);
        txtDiaHora = (TextView) findViewById(R.id.txtDiaHoraAsignarLG);
        edtLocal = (EditText) findViewById(R.id.edtNuevoLocalAsignar);
        if(getIntent().getExtras() != null){
            Grupo grupo = new Grupo();
            EventoEspecial eventoEspecial = new EventoEspecial();
            CicloMateria cicloMateria = new CicloMateria();
            Materia materia = new Materia();
            idDetalleReserva = getIntent().getIntExtra("id", 0);
            detalleReserva.consultar(this, String.valueOf(idDetalleReserva));
            if(detalleReserva.getIdGrupo() != 0){
                grupo.consultar(this, String.valueOf(detalleReserva.getIdGrupo()));
                cicloMateria.consultar(this, String.valueOf(grupo.getIdCicloMateria()));
                materia.consultar(this, String.valueOf(cicloMateria.getCodMateria()));
                //txtMateria.setText(String.valueOf(detalleReserva.getIdGrupo()));
                txtMateria.setText(materia.getCodMateria() + " - " + materia.getNombreMateria());
            }else if(detalleReserva.getIdEventoEspecial() != 0){
                eventoEspecial.consultar(this, String.valueOf(detalleReserva.getIdEventoEspecial()));
                txtMateria.setText(eventoEspecial.getNombreEvento());
            }
            txtDiaHora.setText(getIntent().getStringExtra("diaHora"));
            relacionados = getIntent().getIntArrayExtra("detallesRelacionados");
            //Toast.makeText(this, String.valueOf(detalleReserva.getIdEventoEspecial()), Toast.LENGTH_SHORT).show();
        }
        llenarListaLocales();
    }

    public void llenarListaLocales(){
        local = new Local();
        List objects = local.getLocalesDisponibles(Sesion.getIdusuario(this), detalleReserva.getInicioPeriodoReserva(), detalleReserva.getIdHora(), detalleReserva.getIdDia(), this);
        listaLocalAdapter = new LocalAdapter(this, objects);
        listaLocal.setAdapter(listaLocalAdapter);
    }

    public void guardarNuevoLocalAsignar(View v){
        String local = edtLocal.getText().toString().toUpperCase().trim();
        if(local.equals("")){
            Toast.makeText(this, "Seleccione un local", Toast.LENGTH_SHORT).show();
            return;
        }
        int idLocal = getIdlocal(local);
        if (idLocal == 0){
            Toast.makeText(this, "No existe el local", Toast.LENGTH_SHORT).show();
            return;
        }
        detalleReserva.setIdLocal(idLocal);
        String res = detalleReserva.actualizar(this);
        if(relacionados.length > 0){
            for(int i = 0; i < relacionados.length; i++){
                DetalleReserva detalleRelacionado = new DetalleReserva();
                detalleRelacionado.consultar(this, String.valueOf(relacionados[i]));
                detalleRelacionado.setIdLocal(idLocal);
                detalleRelacionado.actualizar(this);
            }
        }
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    public int getIdlocal(String local){
        int idLocal = 0;
        if (edtLocal.getText().toString().trim().equals(""))
            return -1;
        String sqlLocal = "SELECT * FROM LOCAL WHERE NOMBRELOCAL = '" + local + "'";
        helper.abrir();
        Cursor c2 = helper.consultar(sqlLocal);
        if(c2.moveToFirst()){
            idLocal = c2.getInt(0);
        }
        helper.cerrar();
        return idLocal;
    }

}
