package sv.edu.ues.fia.eisi.parcial3prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EliminarLibro extends AppCompatActivity {

    EditText edtId;
    Button btnEliminar;
    private String urlEliminar = "http://192.168.0.16/carnet/ws_libro_eliminar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_libro);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        edtId = findViewById(R.id.edtIDEliminar);
        btnEliminar = findViewById(R.id.btnEliminarLibro);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = null;
                url = urlEliminar + "?id=" + edtId.getText().toString();
                String urlParse = url.replaceAll(" ", "+");
                ControlServicio.sendRequest(urlParse, getApplication());
                Log.v("URL", urlParse);
            }
        });
    }
}
