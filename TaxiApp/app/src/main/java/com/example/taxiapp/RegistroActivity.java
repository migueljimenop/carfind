package com.example.taxiapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.*;

import com.hbb20.CountryCodePicker;

import cz.msebera.android.httpclient.Header;

public class RegistroActivity extends AppCompatActivity {

    private EditText nomreg, apereg, fnureg, dirreg, emareg, pasreg, cpareg;
    private Button btncrea;
    private TextView volverreg;
    private CountryCodePicker ccp;
    private String country;
    private EditText telreg;
    private RadioButton opcho, opusu;
    private AccesoDatos a;
    private AsyncHttpClient cliente;

    private void agregarUsuario(User u){
        String url = "https://taxiappinacap.000webhostapp.com/agregar.php?";
        String parametros = "nombre="+u.getNombre()+"&apellido="+u.getApellido()+"&fecha="+u.getFechaNac()+"&email="+u.getEmail()
                +"&telefono="+u.getTelefono()+"&direccion="+u.getDireccion()+"&password="+u.getPass()+"&tipo="+u.getTipo();
        cliente.post(url + parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    Toast.makeText(RegistroActivity.this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        a = new AccesoDatos(RegistroActivity.this,"bdtaxiapp",null,1);

        nomreg = (EditText) findViewById(R.id.nomreg);
        apereg = (EditText) findViewById(R.id.apereg);
        fnureg = (EditText) findViewById(R.id.fnureg);
        telreg = (EditText) findViewById(R.id.telreg);
        dirreg = (EditText) findViewById(R.id.dirreg);
        emareg = (EditText) findViewById(R.id.emareg);
        pasreg = (EditText) findViewById(R.id.pasreg);
        cpareg = (EditText) findViewById(R.id.cpareg);

        opcho = (RadioButton) findViewById(R.id.opcho);
        opusu = (RadioButton) findViewById(R.id.opusu);

        ccp = findViewById(R.id.ccp);

        cliente = new AsyncHttpClient();

        btncrea = (Button) findViewById(R.id.btncrea);

        btncrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomreg.getText().toString().replaceAll(" ", "%20");
                String ape = apereg.getText().toString().replaceAll(" ", "%20");
                String fnu = fnureg.getText().toString().replaceAll(" ", "%20");
                String tel = telreg.getText().toString();
                String dir = dirreg.getText().toString().replaceAll(" ", "%20");
                String ema = emareg.getText().toString().replaceAll(" ", "%20");
                String pas = pasreg.getText().toString();
                String cpas = cpareg.getText().toString();

                String tip = opcho.getText().toString().replaceAll(" ", "%20");

                if(opusu.isChecked()){
                    tip = opusu.getText().toString();
                }

                if (nom.trim().length()==0){
                    Toast.makeText(RegistroActivity.this,"Debe ingresar nombre.",Toast.LENGTH_LONG).show();
                    nomreg.requestFocus();
                    return;
                }

                if (ape.trim().length()==0){
                    Toast.makeText(RegistroActivity.this,"Debe ingresar apellido.",Toast.LENGTH_LONG).show();
                    apereg.requestFocus();
                    return;
                }

                if (fnu.trim().length()==0){
                    Toast.makeText(RegistroActivity.this,"Debe ingresar fecha de nacimiento.",Toast.LENGTH_LONG).show();
                    fnureg.requestFocus();
                    return;
                }

                if (pas.trim().length()==0){
                    Toast.makeText(RegistroActivity.this,"Debe ingresar password.",Toast.LENGTH_LONG).show();
                    pasreg.requestFocus();
                    return;
                }

                if (dir.trim().length()==0){
                    Toast.makeText(RegistroActivity.this,"Debe ingresar una dirección.",Toast.LENGTH_LONG).show();
                    dirreg.requestFocus();
                    return;
                }

                if (ema.trim().length()==0){
                    Toast.makeText(RegistroActivity.this,"Debe ingresar email.",Toast.LENGTH_LONG).show();
                    emareg.requestFocus();
                    return;
                }

                if (cpas.trim().length()==0){
                    Toast.makeText(RegistroActivity.this,"Debe ingresar confirmación.",Toast.LENGTH_LONG).show();
                    cpareg.requestFocus();
                    return;
                }

                if(!pas.contentEquals(cpas)){
                    Toast.makeText(RegistroActivity.this,"Las contraseñas no coinciden.",Toast.LENGTH_LONG).show();
                    cpareg.requestFocus();
                    return;
                }

                int t = 0;

                try {
                    t = Integer.parseInt(tel);
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }

                User u = new User(nom,ape,fnu,ema,t,dir,pas,tip);

                long res = a.registrarUsuarios(u);

                agregarUsuario(u);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(res != -1){    // Registró bien.
                    Intent i = new Intent(RegistroActivity.this,MainActivity.class);
                    i.putExtra("USER",u);
                    startActivity(i);
                }else{
                    AlertDialog.Builder msg = new AlertDialog.Builder(RegistroActivity.this);
                    msg.setTitle("Registro");
                    msg.setMessage("Error al registrar el usuario.");
                    msg.setCancelable(true);
                    msg.show();
                }

            }
        });

        volverreg = (TextView) findViewById(R.id.volverreg);

        volverreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistroActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }

    public void onCountryPickerClick(View view) {

        ccp.setDefaultCountryUsingNameCode("+56");
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country = ccp.getDefaultCountryCodeWithPlus();
            }
        });
    }
}

