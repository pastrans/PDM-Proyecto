package sv.edu.ues.fia.eisi.parcial3prueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GestionarLibro extends ListActivity {

    String menu[] = {"ConsultarLibro", "Agregar libro", "Editar libro", "Eliminar libro"};
    String activities[] = {"ConsultarLibro", "NuevoLibro", "EditarLibro", "EliminarLibro"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gestionar_libro);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String nombreValue = activities[position];
        try {
            Class<?> clase = Class.forName("sv.edu.ues.fia.eisi.parcial3prueba." + nombreValue);
            Intent inte = new Intent(this, clase);
            startActivity(inte);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }


}
