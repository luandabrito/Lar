package com.junior.lar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuDoacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_doacao);

    }
    public void doacaoAlimentos (View v){
        startActivity(new Intent(this, DoacaoAlimentos.class));

    }

    public void doacaoMedicamentos (View v){
        startActivity(new Intent(this, DoacaoMedicamentos.class));

    }

    public void doacaoEpi (View v){
        startActivity(new Intent(this, DoacaoEPI.class));

    }



}