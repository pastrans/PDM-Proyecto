package com.example.grupo9pdm115;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ErrorDeUsuario extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_de_usuario);
    }

    public void irALogin(View v){
        String mensaje = "Te la creiste we";
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
