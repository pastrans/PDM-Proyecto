package sv.edu.ues.fia.eisi.parcial3prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NuevoLibro extends AppCompatActivity {

    EditText titulo, autor, categoria, precio;
    Button btnAgregar;
    private String urlInsertar = "http://192.168.0.16/carnet/ws_libro_insertar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_libro);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        titulo = findViewById(R.id.edtTitulo);
        autor = findViewById(R.id.edtAutor);
        categoria = findViewById(R.id.edtCategoria);
        precio = findViewById(R.id.edtPrecio);
        btnAgregar = findViewById(R.id.btnAgregarLibro);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = null;
                url = urlInsertar + "?titulo=" + titulo.getText().toString() + "&autor=" + autor.getText().toString() + "&categoria=" + categoria.getText().toString() + "&precio=" + precio.getText().toString();
                String urlParse = url.replaceAll(" ", "+");
                ControlServicio.sendRequest(urlParse, getApplication());
            }
        });
    }
}
