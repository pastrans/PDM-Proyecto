package com.example.grupo9pdm115.Activities;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

public class IniciarSesion extends CyaneaAppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    EditText nombreUsuario, claveUsuario;
    ControlBD helper;
    SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN = 1;
    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        signInButton = findViewById(R.id.btnGoogleSignIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN);
            }
        });

        helper = new ControlBD(this);
        nombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
        claveUsuario = (EditText) findViewById(R.id.txtClaveUsuario);
        if (Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Esta bandera borra el resto de actividades de la cola
            startActivity(intent);
            finish();
        }
    }

    public void ingresar(View v){
        Sesion sesion = new Sesion();
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario.getText().toString());
        usuario.setClaveUsuario(claveUsuario.getText().toString());
        if (sesion.iniciarSesion(usuario, this)){
            Sesion.setLooggedIn(this, true);
            Sesion.setNombreUsuario(this, usuario.getNombreUsuario());
            Sesion.setIdUsuario(this, usuario.getIdUsuario());
            Intent intent = new Intent(this, MenuPrincipal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Esta bandera borra el resto de actividades de la cola
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(this, "El usuario no existe o la contraseña es incorrecta", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                Usuario usuario = new Usuario();
                usuario.setCorreoPersonal(account.getEmail());
                int existe = usuario.googleUser(this, account.getEmail(), usuario);
                if(existe == 1){
                    if (usuario.verificar(2, this)){
                        Sesion sesion = new Sesion();
                        if(sesion.iniciarSesion(usuario, this)){
                            Sesion.setLooggedIn(this, true);
                            Sesion.setNombreUsuario(this, usuario.getNombreUsuario());
                            Sesion.setIdUsuario(this, usuario.getIdUsuario());
                            startActivity(new Intent(IniciarSesion.this, MenuPrincipal.class));
                            finish();
                        }
                    }else{
                        Toast.makeText(this, "Usuario no registrado en la base de datos", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(this, "Usuario no registrado en la base de datos", Toast.LENGTH_LONG).show();
                    Auth.GoogleSignInApi.signOut(googleApiClient);
                }
            } else{
                Log.v("Result", result.toString());
                Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_LONG).show();
            }
        }
    }
}
