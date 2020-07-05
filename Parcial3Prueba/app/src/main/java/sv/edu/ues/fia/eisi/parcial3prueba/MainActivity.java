package sv.edu.ues.fia.eisi.parcial3prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {


    String menu[] = {"Libro", "Llenar BD"};
    String activities[] = {"GestionarLibro"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(position != 1){
            String nombreValue = activities[position];
            try {
                Class<?> clase = Class.forName("sv.edu.ues.fia.eisi.parcial3prueba." + nombreValue);
                Intent inte = new Intent(this, clase);
                startActivity(inte);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "Llenar BD", Toast.LENGTH_SHORT).show();
            /*helper.abrir();
            String msj = helper.llenarBD();
            helper.cerrar();
            Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();*/
        }
    }

}
