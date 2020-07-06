package com.example.grupo9pdm115.Activities.Local;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.grupo9pdm115.Activities.CicloMateria.ImportarCicloMateria;
import com.example.grupo9pdm115.Activities.TemplatePDF;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.DetalleReserva;
import com.example.grupo9pdm115.Modelos.Dia;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.TipoGrupo;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class verPdf extends AppCompatActivity {
    Local local;
    private TemplatePDF templatePDF;
    private String [] header = {"Hora/Dia","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};
    private int [] numeroDia = new int[] {0,2,3,4,5,6,7};
    ControlBD helper;
    private int dia, mes, anio;
    private List<DetalleReserva> listDetalleReserve ;
    private DetalleReserva detalleReserve ;
    private Ciclo ciclo ;
    private Grupo grupo ;
    private TipoGrupo tipoGrupo;
    private CicloMateria cicloMateria;
    private String regitro;
    private List<Horario>  horas;
    private Horario hora;
    //0, 2, 3, 4, 5,6,7
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pdf);
        local = new Local();
        hora = new Horario();
        grupo = new Grupo();
        tipoGrupo = new TipoGrupo();
        cicloMateria = new CicloMateria();
        detalleReserve = new DetalleReserva();
        pedirPermisos();
        templatePDF = new TemplatePDF(this);
        helper = new ControlBD(this);
        if (getIntent().getExtras() != null){
            local.setIdlocal(getIntent().getIntExtra("idLocal", 0));
            local.setIdtipolocal(getIntent().getIntExtra("idTipoLocal", 0));
            local.setNombreLocal(getIntent().getStringExtra("nombreLocal"));
            local.setCapacidad(getIntent().getIntExtra("capacidad", 0));
            templatePDF.openDocument();
            templatePDF.addMetaData("Horario local","Ventas","Grupo9-PDM115");
            templatePDF.addTitles("HORARIO " + local.getNombreLocal() ,"",ahora());
            templatePDF.createTable(header,getTabla(local.getIdlocal()));
            templatePDF.closeDocument();

//            pdfApp (this);
        }
    }
    public String ahora(){
        int dia, mes, anio;
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);
        return String.format("%d-%02d-%02d", anio, mes + 1, dia);
    }
    private ArrayList<String[]> getClients(){
        ArrayList<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"6:20-8:05","Mat","Mat","Mat","Mat","Mat","Mat"});
        rows.add(new String[]{"8:20-9:50","Mat","Mat","Mat","Mat","Mat","Mat"});
        rows.add(new String[]{"9:55-11:30","Mat","Mat","Mat","Mat","Mat","Mat"});
        rows.add(new String[]{"6:20-8:05","Mat","Mat","Mat","Mat","Mat","Mat"});
        return rows;
    }


    private ArrayList<String[]> getTabla(int idlocal){
        ArrayList<String[]> rows = new ArrayList<>();
        horas = hora.getAll(this);
        listDetalleReserve = detalleReserve.getAllFiltered1(this,idlocal ,ahora()) ;
        // inf para validar las horas
        int contador = 0;
        for( int i = 0; i< listDetalleReserve.size(); i++ ) {
            String[] fila = new String[] {"", "", "", "", "", "", ""};
            detalleReserve = listDetalleReserve.get(i);
            //for para controlar los días de la semana
            contador = 0;
            for( int j = 0; j< numeroDia.length; j++ ) {
                // valido que no sea domingo porque en el horario no presentara ese día
                //Datos quemados en la base
                //para el primer registro encabezado por la horas;
                if(numeroDia[j]== 0){
                    hora.consultar(this, String.valueOf(detalleReserve.getIdHora()));
                    regitro= hora.getHoraInicio() + " - "+hora.getHoraFinal();
                    fila[0] = regitro;
                    contador++;
                }else{
                    if(detalleReserve.getIdDia() == numeroDia[j]){
                        grupo.consultar(this, String.valueOf(detalleReserve.getIdGrupo()));
                        tipoGrupo.consultar(this,String.valueOf(grupo.getIdTipoGrupo()));
                        cicloMateria.consultar(this,String.valueOf(grupo.getIdCicloMateria()));
                        regitro = cicloMateria.getCodMateria() + " " + tipoGrupo.getNombreTipoGrupo()  +" " + grupo.getNumero();
                        contador ++;
                    }else{
                        regitro= "  ";
                    }
                    fila[j] = regitro;
                }
                //Log.v("Array: ", regitro);
            }
            rows.add(fila);
            //Log.v("Tamaño: ", String.valueOf(rows.size()));
            //rows.add(fila);
            /*for(int k = 0; k < rows.size(); k++){
                Log.v("VERPDF 1"+ k, rows.get(k)[0]);
                Log.v("VERPDF 2"+ k, rows.get(k)[1]);
                Log.v("VERPDF 3"+ k, rows.get(k)[2]);
                Log.v("VERPDF 4"+ k, rows.get(k)[3]);
                Log.v("VERPDF 5"+ k, rows.get(k)[4]);
                Log.v("VERPDF 6"+ k, rows.get(k)[5]);
                Log.v("VERPDF 7"+ k, rows.get(k)[6]);
                Log.v("**************** ", "****************");
            }*/
        }
        return rows;
    }
    public void pdfApp (verPdf view){
        templatePDF.appViewPDF( verPdf.this);
    }
    public void pedirPermisos() {
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if(ContextCompat.checkSelfPermission(verPdf.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(verPdf.this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }
    }
/*    public Ciclo getCicloActual(){
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
    }*/
}
