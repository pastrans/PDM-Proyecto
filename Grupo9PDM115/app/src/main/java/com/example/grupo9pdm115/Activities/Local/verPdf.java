package com.example.grupo9pdm115.Activities.Local;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.TemplatePDF;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.DetalleReserva;
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
    private List<Horario>  horas;
    private Horario hora;
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
            templatePDF.openDocument("horario del " + local.getNombreLocal());
            templatePDF.addMetaData("Horario local","Ventas","Grupo9-PDM115");
            templatePDF.addTitles("HORARIO " + local.getNombreLocal() ,"",ahora() +" a las " +hora() );
            templatePDF.createTable(header,getTabla(local.getIdlocal()));
            templatePDF.closeDocument();
            Toast.makeText(getApplicationContext(), "PDF CREADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();
            finish();
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
    public  String hora() {
        final Calendar calendario = Calendar.getInstance();
        String hora = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY));
        String minutos = String.valueOf(calendario.get(Calendar.MINUTE));
        String segundos = String.valueOf(calendario.get(Calendar.SECOND));
        return   hora + ":" + minutos + ":" + segundos;
    }

    private ArrayList<String[]> getTabla(int idlocal){
        ArrayList<String[]> rows = new ArrayList<>();
        horas = hora.getAll(this);
        listDetalleReserve = detalleReserve.getAllFiltered1(this,idlocal ,ahora()) ;
        ArrayList<Integer> filasHoras = new ArrayList<Integer>();
        //Cantidad de filas que debe de tener nuestra tabla
        int contador = 0;
        for( int i = 0; i< listDetalleReserve.size(); i++ ) {
            detalleReserve = listDetalleReserve.get(i);
            for( int k = 0; k< horas.size(); k++ ) {
                hora = horas.get(k);
               if( detalleReserve.getIdHora() == hora.getIdHora()){
                   filasHoras.add(detalleReserve.getIdHora() );
                   contador++;
               }
            }
        }

        filasHoras = eliminarRepetidos(filasHoras) ;
        // controlador de filas

        String regitro = "";
        for( int k = 0; k< filasHoras.size(); k++ ) {
            //controlador de las columnas
            String[] fila = new String[] {"", "", "", "", "", "", ""};
            for( int j = 0; j< numeroDia.length; j++ ) {
                if(numeroDia[j]== 0){
                    hora.consultar(this, String.valueOf(filasHoras.get(k)));
                    regitro= hora.getHoraInicio() + " - "+hora.getHoraFinal();
                    fila[0] = regitro;
                }else{
                    // recorrer el los elemtos
                    int contador1 = numeroDia[j] - 1;
                    for(int i =0 ; i<listDetalleReserve.size(); i++ ){
                        detalleReserve = listDetalleReserve.get(i);
                        //compara cada elemento con el día y hora para ver si debe de llenarse
                        if(detalleReserve.getIdDia() == numeroDia[j] && detalleReserve.getIdHora() == filasHoras.get(k)){
                            grupo.consultar(this, String.valueOf(detalleReserve.getIdGrupo()));
                            tipoGrupo.consultar(this,String.valueOf(grupo.getIdTipoGrupo()));
                            cicloMateria.consultar(this,String.valueOf(grupo.getIdCicloMateria()));
                            regitro = cicloMateria.getCodMateria() + " " + tipoGrupo.getNombreTipoGrupo()  +" " + grupo.getNumero();
                            fila[j] = regitro;
                        }else if(fila[j].equals("")){
                            regitro = " ";
                            fila[j] = regitro;
                        }
                    }
                }
            }
            rows.add(fila);
        }

        return rows;
    }

    private ArrayList<Integer> eliminarRepetidos(ArrayList<Integer> filasHoras) {
        ArrayList<Integer> outArray = new ArrayList<Integer>();
        boolean doAdd = true;
        for (int i = 0; i < filasHoras.size(); i++)
        {
            int elemento = filasHoras.get(i);
            for (int j = 0; j < filasHoras.size(); j++)
            {
                if (i == j)
                {
                    break;
                }
                else if (filasHoras.get(j).equals(elemento))
                {
                    doAdd = false;
                    break;
                }

            }
            if (doAdd)
            {
                outArray.add(elemento);
            }
            else
            {
                doAdd = true;
            }
        }
        return outArray;
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

}
