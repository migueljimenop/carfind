package com.example.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;

import cz.msebera.android.httpclient.Header;


public class ConfirmarViajeActivity extends AppCompatActivity {

    private TextView ori, des, dis, dur, tar, back;
    private Button btnconf;
    private AsyncHttpClient cliente;

    private void agregarViaje(Viaje v){
        String url = "https://taxiappinacap.000webhostapp.com/agregarViaje.php?";
        String or = stripAccents(v.getOrigen());
        String de = stripAccents(v.getDestino());
        String parametros = "origen="+or+"&destino="+de+"&distancia="+v.getDistancia()+"&duracion="+v.getTiempo()
                +"&tarifa="+v.getTarifa();
        String link = url+parametros;
        Log.d("DIRECCION ----->", link);
        cliente.post(link, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    Toast.makeText(ConfirmarViajeActivity.this, "Viaje registrado correctamente.", Toast.LENGTH_SHORT).show();
                    //Limpiar campos?
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("\u0027","");
        return s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_viaje);

        ori = findViewById(R.id.origen);
        des = findViewById(R.id.destino);
        dis = findViewById(R.id.distancia);
        dur = findViewById(R.id.duracion);
        tar = findViewById(R.id.tarifa);
        back = findViewById(R.id.volvercvia);
        btnconf = findViewById(R.id.btnconfirm);
        cliente = new AsyncHttpClient();

        String jsonResult = getIntent().getExtras().getString("json");

        JSONObject jsonObject = null;

        try {

            jsonObject = new JSONObject(jsonResult);
            // routesArray contains ALL routes
            JSONArray routesArray = jsonObject.getJSONArray("routes");
            // Grab the first route
            JSONObject route = routesArray.getJSONObject(0);
            // Take all legs from the route
            JSONArray legs = route.getJSONArray("legs");
            // Grab first leg
            JSONObject leg = legs.getJSONObject(0);

            JSONObject durationObject = leg.getJSONObject("duration");
            JSONObject distanceObject = leg.getJSONObject("distance");

            String duration = durationObject.getString("text");
            String distance = distanceObject.getString("text");
            String origen = leg.getString("start_address");
            String destino = leg.getString("end_address");

            ori.setText("Origen: " + origen);
            des.setText("Destino: " + destino);
            dis.setText("Distancia: " + distance);
            dur.setText("Duración: " + duration);

            String distancia = distance.replaceAll("[^0-9?!\\.]","");
            String duracion  = duration.replaceAll("[^0-9?!\\.]","");

            float dist = Float.parseFloat(distancia);
            int dura = Integer.parseInt(duracion);
            float tarifa = 0;


            // Calculamos la tarifa en base a la distancia.
            // Con valores definidos por los kilometros totales.

            if(dist >= 0.1 && dist <= 1.0){
               tarifa = dist * 700;
            }else if (dist >= 1.1 && dist <= 2.0){
                tarifa = dist * 800;
            }else if (dist >= 2.1 && dist <= 3.0){
                tarifa = dist * 1000;
            }else if (dist >=3.1){
                tarifa = dist * 1500;
            }

            tar.setText("Tarifa: $" + tarifa);

            int d = Math.round(dist);

            String s = origen.replaceAll(" ", "%20");
            String e = destino.replaceAll(" ", "%20");

            final Viaje v = new Viaje(s,e,d,dura,(int)tarifa);

            //Agregar viaje realizado una vez que confirma.

            btnconf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    agregarViaje(v);
                    Intent i = new Intent(ConfirmarViajeActivity.this,MainActivity.class);
                    startActivity(i);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(ConfirmarViajeActivity.this,MapaActivity.class);
            startActivity(i);
            }
        });
    }
}