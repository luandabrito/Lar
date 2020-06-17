package com.junior.lar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Estatistica extends AppCompatActivity {

    public void mostrarValorMed(View v){
        DoacaoMedicamentos obj = new DoacaoMedicamentos();
        int contador = obj.getContador();
        TextView campoTexto = (TextView) findViewById(R.id.QtdMed);
       // String mensagem = Integer.toString(contador);
        campoTexto.setText (contador);
    }

    public void mostrarValorAlimentos(View v){
        DoacaoAlimentos obj = new DoacaoAlimentos();
        int contador = obj.getContador();
        TextView campoTexto = (TextView) findViewById(R.id.qtdAlimentos);
        // String mensagem = Integer.toString(contador);
        campoTexto.setText (contador);
    }

    public void mostrarValorEPi(View v){
        DoacaoEPI obj = new DoacaoEPI();
        int contador = obj.getContador();
        TextView campoTexto = (TextView) findViewById(R.id.qtdEpis);
        // String mensagem = Integer.toString(contador);
        campoTexto.setText (contador);
    }

    public void mostrarValorRoupas(View v){
        DoacaoRoupas obj = new DoacaoRoupas();
        int contador = obj.getContador();
        TextView campoTexto = (TextView) findViewById(R.id.qtdRoupas);
        // String mensagem = Integer.toString(contador);
        campoTexto.setText (contador);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica);

        mostrarValorAlimentos(null);
        mostrarValorEPi(null);
        mostrarValorMed(null);
        mostrarValorRoupas(null);
    }
    public void voltar(View v) {
        finish();
    }
}
