package com.example.grupo9pdm115.Activities.Local;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.grupo9pdm115.Activities.TemplatePDF;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.Calendar;

public class verPdf extends AppCompatActivity {
    Local local;
    private TemplatePDF templatePDF;
    private String [] header = {"Hora/Dia,","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pdf);
        local = new Local();

        templatePDF = new TemplatePDF(this);
        if (getIntent().getExtras() != null){
            local.setIdlocal(getIntent().getIntExtra("idLocal", 0));
            local.setIdtipolocal(getIntent().getIntExtra("idTipoLocal", 0));
            local.setNombreLocal(getIntent().getStringExtra("nombreLocal"));
            local.setCapacidad(getIntent().getIntExtra("capacidad", 0));
            templatePDF.openDocument();
            templatePDF.addMetaData("Horario local","Ventas","Grupo9-PDM115");
            templatePDF.addTitles("HORARIO " + local.getNombreLocal() ,"",ahora());
            templatePDF.createTable(header,getClients());
            templatePDF.closeDocument();
            pdfApp (this);

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
    public void pdfApp (verPdf view){
        templatePDF.appViewPDF(this);

    }
}
