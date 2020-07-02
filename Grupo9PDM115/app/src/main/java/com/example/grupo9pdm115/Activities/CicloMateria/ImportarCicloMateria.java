package com.example.grupo9pdm115.Activities.CicloMateria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar_ciclo_materia);
        btnExaminar = findViewById(R.id.btnExaminar);
        rvUsuarios = findViewById(R.id.rvUsuarios);
        btnImportar = findViewById(R.id.btnImportar);
        edtiRuta = findViewById(R.id.editTextRuta);
        rvUsuarios.setLayoutManager(new GridLayoutManager(  this, 1));
        pedirPermisos();
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
    }

    public void importarCSV() {
        final String[] split = uri.getPath().split(":");//split the path.
        Log.i("CicloMateriaImportar", "vemoas:   "+uri.getPath());
        String filePath = split[1];//assign it to a string(your choice).
        File archivo = new File(Environment.getExternalStorageDirectory() + "/"+ filePath);
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
                    cm.setIdCiclo(1);
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
            String FilePath = data.getData().getPath();
            edtiRuta.setText(FilePath);
            uri = data.getData(); //obtener el uri content
        }
    }

}