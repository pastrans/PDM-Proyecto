package sv.edu.ues.fia.eisi.parcial3prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ConsultarLibro extends AppCompatActivity {

    String menu[] = {"Agregar libro", "Editar libro", "Eliminar libro"};
    String activities[] = {"NuevoLibro", "EditarLibro", "EliminarLibro"};

    private String urlConsultar = "http://192.168.0.16/carnet/ws_libro_listar.php";

    ListView listViewLibros;
    EditText filtro;
    Button btnBuscarLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_libro);
        //setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        listViewLibros = findViewById(R.id.listViewLibros);
        btnBuscarLibro = findViewById(R.id.btnBuscarLibro);
        filtro = findViewById(R.id.edtBuscarLibro);
        llenarListaLibros(null);
        btnBuscarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewLibros.setAdapter(null);
                llenarListaLibros(filtro.getText().toString());
            }
        });
    }

    public void llenarListaLibros(String filtro){
        String urlFinal = "";
        if (filtro != null)
            urlFinal = urlConsultar + "?titulo=" + filtro;
        else
            urlFinal = urlConsultar;
        Log.v("URL", urlFinal);
        String dataString = ControlServicio.obtenerRespuestaPeticion(urlFinal, this);
        List<Libro> dataLibro = new ArrayList<Libro>();
        List<String> data = new ArrayList<String>();
        dataLibro.addAll(ControlServicio.obtenerLibrosL(dataString, this));
        for (int i = 0; i < dataLibro.size(); i++){
            Libro libro = dataLibro.get(i);
            data.add(libro.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listViewLibros.setAdapter(adapter);
    }
}
