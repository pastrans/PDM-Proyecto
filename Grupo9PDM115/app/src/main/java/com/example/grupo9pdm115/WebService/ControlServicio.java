package com.example.grupo9pdm115.WebService;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.Unidad;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ControlServicio {

    public static String obtenerRespuestaPeticion(String url, Context ctx) {

        String respuesta = " ";

        // Estableciendo tiempo de espera del servicio
        HttpParams parametros = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(parametros, 3000);
        HttpConnectionParams.setSoTimeout(parametros, 5000);

        // Creando objetos de conexion
        HttpClient cliente = new DefaultHttpClient(parametros);
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpRespuesta = cliente.execute(httpGet);
            StatusLine estado = httpRespuesta.getStatusLine();
            int codigoEstado = estado.getStatusCode();
            if (codigoEstado == 200) {
                HttpEntity entidad = httpRespuesta.getEntity();
                respuesta = EntityUtils.toString(entidad);
            }
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_LONG).show();
            // Desplegando el error en el LogCat
            Log.v("Error de Conexion", e.toString());
        }
        return respuesta;
    }

    public static String obtenerRespuestaPost(String url, JSONObject obj, Context ctx) {
        String respuesta = " ";
        try {
            HttpParams parametros = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(parametros, 3000);
            HttpConnectionParams.setSoTimeout(parametros, 5000);
            HttpClient cliente = new DefaultHttpClient(parametros);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("content-type", "application/json");
            StringEntity nuevaEntidad = new StringEntity(obj.toString());
            httpPost.setEntity(nuevaEntidad);
            //Log.v("Data: ", nuevaEntidad.toString());
            Log.v("Nombre", obj.get("nombreent").toString());
            Log.v("Peticion",url);
            Log.v("POST", httpPost.toString());
            HttpResponse httpRespuesta = cliente.execute(httpPost);
            StatusLine estado = httpRespuesta.getStatusLine();

            int codigoEstado = estado.getStatusCode();
            if (codigoEstado == 200) {
                respuesta = Integer.toString(codigoEstado);
                Log.v("respuesta",respuesta);
            }
            else{
                Log.v("respuesta",Integer.toString(codigoEstado));
            }
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_LONG)
                    .show();
            // Desplegando el error en el LogCat
            Log.v("Error de Conexion", e.toString());
        }
        return respuesta;
    }

    public static List<Unidad> obtenerUnidades(String json, Context ctx) {

        List<Unidad> listaUnidad = new ArrayList<Unidad>();

        try {
            JSONArray unidadJson = new JSONArray(json);
            for (int i = 0; i < unidadJson.length(); i++) {
                JSONObject obj = unidadJson.getJSONObject(i);
                Unidad unidad = new Unidad();
                unidad.setIdUnidad(Integer.valueOf(obj.getString("IDUNIDAD")));
                unidad.setNombreent(obj.getString("NOMBREENT"));
                unidad.setDescripcionent(obj.getString("DESCRIPCIONENT"));
                listaUnidad.add(unidad);
            }
            return listaUnidad;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseo de JSON", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public static void sendRequest(String peticion, Context ctx) {
        String json = obtenerRespuestaPeticion(peticion, ctx);
        try {
            JSONObject resultado = new JSONObject(json);
            //Toast.makeText(ctx, "Registro ingresado " + String.valueOf(resultado.getInt("resultado")), Toast.LENGTH_LONG).show();
            int respuesta = resultado.getInt("resultado");
            if (respuesta == 1)
                Toast.makeText(ctx, "Operación realizada con éxito", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ctx, "Error al realizar la operación", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void insertarPost(String url, JSONObject obj, Context ctx) {
        String respuesta = obtenerRespuestaPost(url, obj, ctx);
        try {
            if(respuesta.equals("200"))
                Toast.makeText(ctx, "Insercion Correcta", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ctx, "Error registro duplicado", Toast.LENGTH_LONG).show();
            Log.v("",respuesta);
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en la respuesta JSON", Toast.LENGTH_LONG).show();
        }
    }

}
