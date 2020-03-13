package com.example.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private ImageButton salir, acerca, cuenta, misviajes, addviaje;
    private TextView tvlog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salir = (ImageButton) findViewById(R.id.salir);
        acerca = (ImageButton) findViewById(R.id.acerca);
        cuenta = (ImageButton) findViewById(R.id.cuenta);
        misviajes = (ImageButton) findViewById(R.id.misviajes);
        addviaje = (ImageButton) findViewById(R.id.addviaje);

        addviaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MapaActivity.class);
                startActivity(i);
                return;
            }
        });

        misviajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ViajesRealizadosActivity.class);
                startActivity(i);
                return;
            }
        });


         cuenta.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(MainActivity.this,CuentaActivity.class);
                 startActivity(i);
                 return;
             }
         });


         acerca.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(MainActivity.this,AcercaActivity.class);
                 startActivity(i);
                 return;
             }
         });

         salir.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(MainActivity.this,LoginActivity.class);
                 startActivity(i);
                 return;
             }
         });
    }
}


