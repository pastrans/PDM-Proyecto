package com.example.grupo9pdm115.Activities.Local;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grupo9pdm115.R;

public class GestionarLocal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_local);
    }

    public void btnNuevoGLocal(View v){
        Intent inte = new Intent(this, NuevoLocal.class);
        startActivity(inte);
    }

}
