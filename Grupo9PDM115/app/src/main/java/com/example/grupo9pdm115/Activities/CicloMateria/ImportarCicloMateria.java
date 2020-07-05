package com.example.grupo9pdm115.Activities.CicloMateria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.ImportarCicloMateriaAdapter;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ImportarCicloMateria extends AppCompatActivity {
    private final  int picker = 1;
    RecyclerView rvUsuarios;
    Button btnImportar;
    Button btnExaminar;
    EditText edtiRuta;
    Uri uri;
    List<CicloMateria> listaCicloMateria = new ArrayList<>();
    ImportarCicloMateriaAdapter adaptador;
    private int VALOR_RETORNO = 1;
    private int dia, mes, anio;
    ControlBD helper;
    Ciclo cicloActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar_ciclo_materia);
        btnExaminar = findViewById(R.id.btnExaminar);
        rvUsuarios = findViewById(R.id.rvUsuarios);
        btnImportar = findViewById(R.id.btnImportar);
        edtiRuta = findViewById(R.id.editTextRuta);
        edtiRuta.setEnabled(false);
        edtiRuta.setFocusable(false);
        rvUsuarios.setLayoutManager(new GridLayoutManager(  this, 1));
        pedirPermisos();
        helper = new ControlBD(this);
        btnImportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importarCSV();
            }
        });

        btnExaminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filePicker();
            }
        });
        cicloActual = new Ciclo();
        cicloActual = getCicloActual();

    }

    public void importarCSV() {
        if(!edtiRuta.getText().toString().equals("")){
            String texto = uri.getPath();
            String palabra = ".csv";
            if(texto.contains(palabra)){

                final String[] split = uri.getPath().split("/external_files/");//split the path.
                final String[] split2 = uri.getPath().split(":");//split the path.
                Log.v("length" , String.valueOf(split.length));

                if(split.length == 1 && split2.length == 1 ){
                    Toast.makeText(this, "El archivo no se encuentra en la raíz", Toast.LENGTH_SHORT).show();
                    return;
                }
                String filePath = "";

                Log.i("CicloMateriaImportar", "vemoas:   "+uri.getPath());
                if(split.length > 1 ){
                    filePath = split[1];
                }
                if(split2.length > 1 ){
                    filePath = split2[1];
                }

                //File archivo = new File(Environment.getExternalStorageDirectory() + "/"+ filePath);
                File archivo = new File(Environment.getExternalStorageDirectory() + "/" + filePath);
                Log.i("CicloMateriaImportar", "vemoas:   "+Environment.getExternalStorageDirectory());
                Log.i("CicloMateriaImportar", "vemoas:   "+Environment.getExternalStorageState());
                Log.i("CicloMateriaImportar", "vemoas:   "+archivo.getAbsolutePath());
                if(archivo.exists()) {
                    String cadena;
                    String[] arreglo;
                    try {
                        FileReader fileReader = new FileReader(Environment.getExternalStorageDirectory() + "/"+ filePath);

                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        CicloMateria cm;
                        while ((cadena = bufferedReader.readLine()) != null) {

                            arreglo = cadena.split(";");
                            cm = new CicloMateria();
                            cm.setIdCiclo(cicloActual.getIdCiclo());
                            cm.setCodMateria(arreglo[0]);
                            listaCicloMateria.add(cm);

                            cm.guardar(this);
                            Toast.makeText(ImportarCicloMateria.this, "SE IMPORTO EXITOSAMENTE", Toast.LENGTH_SHORT).show();

                            adaptador = new ImportarCicloMateriaAdapter(ImportarCicloMateria.this, listaCicloMateria);
                            rvUsuarios.setAdapter(adaptador);
                        }

                    } catch (Exception e) {
                    }
                }else {
                    Toast.makeText(this, "No existe el archivo", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "La extención del archivo debe de ser .CSV", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Seleccione un archivo", Toast.LENGTH_SHORT).show();
        }

    }

    public void pedirPermisos() {
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if(ContextCompat.checkSelfPermission(ImportarCicloMateria.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ImportarCicloMateria.this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }
    }

    public void filePicker(){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);
        try {
            startActivityForResult(intent.createChooser( intent,"Seleccione un archivo para subir") , picker );

        }catch (Exception e){

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            //Cancelado por el usuario
        }
        if ((resultCode == RESULT_OK) && (requestCode == VALOR_RETORNO )) {
            //Procesar el resultado
            Log.v("Uri: ", data.toURI());
            String FilePath = data.getData().getPath();
            edtiRuta.setText(FilePath);
            uri = data.getData(); //obtener el uri content
        }
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
    public void ahora(){
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);
    }



}