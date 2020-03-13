package com.example.taxiapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ViajesRealizadosActivity extends AppCompatActivity {
    private ListView lvViajes;
    private TextView btnvolviajes;
    private AsyncHttpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes_realizados);

        lvViajes = (ListView) findViewById(R.id.lvViajes);

        cliente = new AsyncHttpClient();

        obtenerViajes();

        btnvolviajes = (TextView) findViewById(R.id.btnbackviajes);

        btnvolviajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViajesRealizadosActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void obtenerViajes(){
        String url = "https://taxiappinacap.000webhostapp.com/obtenerViaje.php";

        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    listarViajes(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void listarViajes(String respuesta){
        final ArrayList<Viaje> lista = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(respuesta);
            for(int i=0; i<json.length(); i++){
                Viaje v = new Viaje();
                v.setId(json.getJSONObject(i).getString("id_via"));
                v.setOrigen(json.getJSONObject(i).getString("ori_via"));
                v.setDestino(json.getJSONObject(i).getString("des_via"));
                v.setDistancia(json.getJSONObject(i).getInt("dis_via"));
                v.setTiempo(json.getJSONObject(i).getInt("tie_via"));
                v.setTarifa(json.getJSONObject(i).getInt("tar_via"));
                lista.add(v);
            }

            ArrayAdapter<User> a = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
            lvViajes.setAdapter(a);

            lvViajes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Viaje v = lista.get(i);
                    String url = "https://taxiappinacap.000webhostapp.com/eliminarViaje.php?id_via="+v.getId();
                    cliente.post(url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200){
                                Toast.makeText(ViajesRealizadosActivity.this, "Viaje eliminado correctamente.", Toast.LENGTH_SHORT).show();
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                obtenerViajes();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                    return true;
                }
            });

            lvViajes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Viaje v = lista.get(i);
                    StringBuffer b = new StringBuffer();
                    b.append("ID: "+ v.getId() + "\n");
                    b.append("Origen: "+ v.getOrigen() + "\n");
                    b.append("Destino: "+ v.getDestino() + "\n");
                    b.append("Distancia: "+ v.getDistancia() + " km." + "\n");
                    b.append("Duración: "+ v.getTiempo() + " min." + "\n");
                    b.append("Tarifa: $"+ v.getTarifa());
                    AlertDialog.Builder a = new AlertDialog.Builder(ViajesRealizadosActivity.this);
                    a.setCancelable(true);
                    a.setTitle("Detalle viajes realizados");
                    a.setMessage(b.toString());
                    a.setIcon(R.drawable.comprobado);
                    a.show();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
