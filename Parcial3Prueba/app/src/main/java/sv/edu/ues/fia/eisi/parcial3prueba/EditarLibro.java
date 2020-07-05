package sv.edu.ues.fia.eisi.parcial3prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditarLibro extends AppCompatActivity {

    EditText titulo, autor, categoria, precio, id;
    Button btnEditar;
    private String urlEditar = "http://192.168.0.16/carnet/ws_libro_actualizar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_libro);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        id = findViewById(R.id.edtID);
        titulo = findViewById(R.id.edtEditarTitulo);
        autor = findViewById(R.id.edtEditarAutor);
        categoria = findViewById(R.id.edtEditarCategoria);
        precio = findViewById(R.id.edtEditarPrecio);
        btnEditar = findViewById(R.id.btnEditarLibro);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = null;
                url = urlEditar + "?titulo=" + titulo.getText().toString() + "&autor=" + autor.getText().toString() + "&categoria=" + categoria.getText().toString() + "&precio=" + precio.getText().toString() + "&id=" + id.getText().toString();
                String urlParse = url.replaceAll(" ", "+");
                ControlServicio.sendRequest(urlParse, getApplication());
                Log.v("URL", urlParse);
            }
        });
    }
}
