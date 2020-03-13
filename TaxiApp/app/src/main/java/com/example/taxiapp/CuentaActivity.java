package com.example.taxiapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.*;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CuentaActivity extends AppCompatActivity {

    private ListView lvUsuario;
    private TextView btnbackmc;
    private AsyncHttpClient cliente;


    private void obtenerUsuarios(){
        String url = "https://taxiappinacap.000webhostapp.com/obtener.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    listarUsuarios(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void listarUsuarios(String respuesta){
        final ArrayList<User> lista = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(respuesta);
            for(int i=0; i<json.length(); i++){
                User u = new User();
                u.setId(json.getJSONObject(i).getString("id_usu"));
                u.setNombre(json.getJSONObject(i).getString("nom_usu"));
                u.setApellido(json.getJSONObject(i).getString("ape_usu"));
                u.setFechaNac(json.getJSONObject(i).getString("fnu_usu"));
                u.setEmail(json.getJSONObject(i).getString("ema_usu"));
                u.setTelefono(json.getJSONObject(i).getInt("tel_usu"));
                u.setDireccion(json.getJSONObject(i).getString("dir_usu"));
                u.setPass(json.getJSONObject(i).getString("pas_usu"));
                u.setTipo(json.getJSONObject(i).getString("tip_usu"));
                lista.add(u);
            }

            ArrayAdapter<User> a = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
            lvUsuario.setAdapter(a);

            lvUsuario.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    User u = lista.get(i);
                    String url = "https://taxiappinacap.000webhostapp.com/eliminar.php?id_usu="+u.getId();
                    cliente.post(url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200){
                                Toast.makeText(CuentaActivity.this, "Usuario eliminado correctamente.", Toast.LENGTH_SHORT).show();
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                obtenerUsuarios();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                    return true;
                }
            });

            lvUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    User u = lista.get(i);
                    StringBuffer b = new StringBuffer();
                    b.append("ID: "+ u.getId() + "\n");
                    b.append("Nombre completo: "+ u.getNombre() + " " + u.getApellido()  +  "\n");
                    b.append("Email: "+ u.getEmail());
                    AlertDialog.Builder a = new AlertDialog.Builder(CuentaActivity.this);
                    a.setCancelable(true);
                    a.setTitle("Detalle del usuario");
                    a.setMessage(b.toString());
                    a.setIcon(R.drawable.comprobado);
                    a.show();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        lvUsuario = (ListView) findViewById(R.id.lvUsuario);

        cliente = new AsyncHttpClient();

        obtenerUsuarios();

        btnbackmc = (TextView) findViewById(R.id.btnbackmc);

        btnbackmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(CuentaActivity.this,MainActivity.class);
            startActivity(i);
            }
        });
    }


}
