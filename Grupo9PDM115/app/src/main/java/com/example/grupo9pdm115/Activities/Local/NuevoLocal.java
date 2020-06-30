package com.example.grupo9pdm115.Activities.Local;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.TipoLocalSpinner;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class NuevoLocal extends CyaneaAppCompatActivity {

    EditText nombreLocal;
    EditText capcidad;
    Spinner tipoLocalSpinner;
    TipoLocalSpinner tipoLocalAdapter;
    int PERMISO_LECTURA_EXTERNA;
    int PERMISO_ESCRITURA_EXTERNA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "ILO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_local);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISO_LECTURA_EXTERNA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISO_ESCRITURA_EXTERNA);
        }
        nombreLocal = (EditText) findViewById(R.id.editNombreLocal);
        capcidad = (EditText) findViewById(R.id.editCapacidad);

        // Spinner
        tipoLocalSpinner = (Spinner) findViewById(R.id.tipoLocalSpinner);
        tipoLocalAdapter = new TipoLocalSpinner(this);
        tipoLocalSpinner.setAdapter(tipoLocalAdapter.getAdapterTipoLocal(this));
    }

    public void btnAgregarNLocal(View v) throws WriterException, FileNotFoundException {
        String regInsertados;
        Local local = new Local();
        int posTipoLocal = tipoLocalSpinner.getSelectedItemPosition();
        local.setNombreLocal(nombreLocal.getText().toString());
        local.setCapacidad(Integer.parseInt(capcidad.getText().toString()));
        local.setIdtipolocal(tipoLocalAdapter.getIdTipoLocal(posTipoLocal));
        if(local.getNombreLocal().isEmpty()){
            regInsertados="EL nombre está vacio";
        }
        else{
            if(local.getCapacidad() <= 0){
                regInsertados = "La capacidad debe ser mayor a cero";
            }else {
                if (local.getIdtipolocal() == 0) {
                    regInsertados = "Escoga un tipo de local";
                } else {
                    regInsertados = local.guardar(this);
                    /*MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    BitMatrix bitMatrix = multiFormatWriter.encode(txt, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imgViewQR.setImageBitmap(bitmap);*/

                    //GUARDAR IMAGEN EN EL TELÉFONO
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    BitMatrix bitMatrix = multiFormatWriter.encode(local.getNombreLocal(), BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    String parent = "/ReservaLocales/QR";
                    String filename = parent + "/qrCode" + local.getNombreLocal() + ".png";

                    File parentDirectory = new File(Environment.getExternalStorageDirectory() + parent);
                    if(!parentDirectory.exists()){
                        parentDirectory.mkdirs();
                    }

                    File dest = new File(Environment.getExternalStorageDirectory() + "/" + filename);
                    FileOutputStream outputStream = new FileOutputStream(dest);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    limpiar();
                }
            }
        }
        //regInsertados = local.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    private void limpiar() {
        nombreLocal.setText("");
        capcidad.setText("");
        tipoLocalSpinner.setSelection(0);
    }

    public void btnLimpiarNLocal(View v){
    limpiar();
    }

}
