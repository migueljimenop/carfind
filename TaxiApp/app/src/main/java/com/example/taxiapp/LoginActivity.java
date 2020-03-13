package com.example.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;


public class LoginActivity extends AppCompatActivity {

    private AccesoDatos a;
    private EditText txtlog, txtpas;
    private Switch swru;
    private TextView btnreg;
    private Button btning;
    private AsyncHttpClient cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        a = new AccesoDatos(LoginActivity.this,"bdtaxiapp",null,1);

        txtlog = (EditText) findViewById(R.id.txtlog);
        txtpas = (EditText) findViewById(R.id.txtpas);

        btning = (Button) findViewById(R.id.btning);
        btnreg = (TextView) findViewById(R.id.btnreg);

        txtlog.setInputType(InputType.TYPE_NULL);

        cliente = new AsyncHttpClient();

        ClickLogin();

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegistroActivity.class);
                startActivity(i);
            }
        });

        btning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String log = txtlog.getText().toString();
                String pas = txtpas.getText().toString();

                if (log.trim().length()==0){
                    Toast.makeText(LoginActivity.this,"Debe ingresar usuario.",Toast.LENGTH_LONG).show();
                    txtlog.requestFocus();
                    return;
                }

                if (pas.trim().length()==0){
                    Toast.makeText(LoginActivity.this,"Debe ingresar password.",Toast.LENGTH_LONG).show();
                    txtpas.requestFocus();
                    return;
                }

                User u = a.consultarUser(log);


                if(u != null){ //Si es true, quiere decir que lo encontró.
                    String user  = u.getEmail();
                    int telephone = u.getTelefono();
                    String pass = u.getPass();
                    if (pas.equals(pass) && log.equals(String.valueOf(telephone))){
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        User usr = new User(log, pas);
                        i.putExtra("LUSER", usr);
                        startActivity(i);
                    }
                    if (pas.equals(pass) && log.equals(user)){
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        User usr = new User(log,pas);
                        i.putExtra("LUSER",usr);
                        startActivity(i);
                    }else{
                        Toast.makeText(LoginActivity.this,"Usuario/Password incorrecta.",Toast.LENGTH_LONG).show();
                        txtlog.requestFocus();
                        return;
                    }
                }else{
                    txtpas.setText("");
                    Toast.makeText(LoginActivity.this,"No se encuentra el usuario registrado.",Toast.LENGTH_LONG).show();
                    txtlog.requestFocus();
                    return;
                }
            }
        });
    }

    private void ClickLogin(){
        txtlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtlog.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });

    }
}
