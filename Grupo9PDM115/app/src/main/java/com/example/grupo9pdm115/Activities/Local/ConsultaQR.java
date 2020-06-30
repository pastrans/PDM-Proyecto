package com.example.grupo9pdm115.Activities.Local;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.DetalleReservaAdapter;
import com.example.grupo9pdm115.Adapters.EventosQRAdapter;
import com.example.grupo9pdm115.Adapters.LocalAdapter;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.DetalleReserva;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Utilidades.DetallesQR;
/*import com.example.grupo9pdm115.Utilidades.FechasHelper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConsultaQR extends AppCompatActivity {

    /*EditText qr;
    ImageView imgViewQR;
    ListView listaEventos;
    DetalleReserva detalleReserva;
    List<Integer> idsAgrupados, posRemover;
    EventosQRAdapter eventosQRAdapter;
    TextView txtConsultarQR;
    ControlBD helper;
    int PERMISO_LECTURA_EXTERNA;
    int PERMISO_ESCRITURA_EXTERNA;
    private IntentIntegrator qrScan;
    int CAMERA_REQUEST_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_q_r);*/
        //qr = (EditText) findViewById(R.id.nombreLocalQR);
        //imgViewQR = (ImageView) findViewById(R.id.imgViewQR);
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISO_LECTURA_EXTERNA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISO_ESCRITURA_EXTERNA);
        }
        txtConsultarQR = (TextView) findViewById(R.id.txtConsultarQR);
        txtConsultarQR.setVisibility(View.GONE);
        qrScan = new IntentIntegrator(this);
        listaEventos = (ListView) findViewById(R.id.listaEventosQR);
        helper = new ControlBD(this);
    }

    public void generarQR(View v) throws WriterException, FileNotFoundException {
        String txt = qr.getText().toString();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(txt, BarcodeFormat.QR_CODE, 200, 200);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        imgViewQR.setImageBitmap(bitmap);

        //GUARDAR IMAGEN EN EL TELÉFONO
        String parent = "/ReservaLocales/QR";
        String filename = parent + "/qrCode" + txt + ".png";

        File parentDirectory = new File(Environment.getExternalStorageDirectory() + parent);
        if(!parentDirectory.exists()){
            parentDirectory.mkdirs();
        }

        File dest = new File(Environment.getExternalStorageDirectory() + "/" + filename);
        FileOutputStream outputStream = new FileOutputStream(dest);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
    }

    public void llenarListaLocales(String fecha, String local) throws ParseException {
        listaEventos.setAdapter(null);
        detalleReserva = new DetalleReserva();
        posRemover = new ArrayList<>();
        String res = "", horaFinal = "";
        int idEventoEspecialAnterior = 0, idLocalAnterior = 0, idDiaAnterior = 0, idHoraAnterior = 0, countAtras = 0;
        int idDia = getIdDia(getNombreDia(FechasHelper.cambiarFormatoIsoALocal(ahora())));
        List detalles = detalleReserva.getEventosDia(this, idDia, local, fecha);
        if(detalles.size() == 0){
            txtConsultarQR.setVisibility(View.VISIBLE);
            return;
        }
        txtConsultarQR.setVisibility(View.GONE);
        List<DetallesQR> detallesQR = new ArrayList<DetallesQR>();
        for (int i = 0; i < detalles.size(); i++){
            DetalleReserva d = (DetalleReserva) detalles.get(i);
            DetallesQR detalleQRE = new DetallesQR();
            detalleQRE.setDetalleReserva(d);
            if(idEventoEspecialAnterior == d.getIdEventoEspecial() && (idHoraAnterior + 1) == d.getIdHora() && d.getIdEventoEspecial() != 0 && idHoraAnterior != 0){
                countAtras++;
                Horario horafin = new Horario();
                horafin.consultar(this, String.valueOf(d.getIdHora()));
                DetallesQR detalleModificar = detallesQR.get(i - countAtras);
                detalleQRE.setHoraFinal(horafin.getHoraFinal());
                detalleModificar.setHoraFinal(horafin.getHoraFinal());
                //detalleModificar.setIdHora(horafin.getIdHora());
                posRemover.add(i);
            }
            idEventoEspecialAnterior = d.getIdEventoEspecial();
            idHoraAnterior = d.getIdHora();
            detallesQR.add(detalleQRE);
        }
        for (int j = 0; j < posRemover.size(); j++){
            int id = posRemover.get(j) - j;
            detallesQR.remove(id);
            detalles.remove(id);
        }
        eventosQRAdapter = new EventosQRAdapter(this, detallesQR);
        listaEventos.setAdapter(eventosQRAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "No encontrado", Toast.LENGTH_LONG).show();
            }else{
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                try {
                    llenarListaLocales(ahora(), result.getContents());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Log.v("Result: ", result.getContents());
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
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

    public void escanearQR(View v){
        qrScan.initiateScan();
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
    }*/

}
