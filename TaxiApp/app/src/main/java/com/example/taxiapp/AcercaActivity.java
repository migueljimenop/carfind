package com.example.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class AcercaActivity extends AppCompatActivity {
    private TextView btnback, namedev2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);

        btnback = (TextView) findViewById(R.id.btnback);
        namedev2 = (TextView) findViewById(R.id.namedev2);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AcercaActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        namedev2.setMovementMethod(LinkMovementMethod.getInstance());
        namedev2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://github.com/migueljimenop"));
                startActivity(browserIntent);
            }
        });
    }
}
