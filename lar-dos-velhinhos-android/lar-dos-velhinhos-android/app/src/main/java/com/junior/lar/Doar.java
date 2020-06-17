package com.junior.lar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class Doar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao);
    }
    public void exibirMensagem(View v){
        Context context = getApplicationContext();
        CharSequence text = "Agradecemos sua DOAÇÃO!";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
        toast.show();
    }
    public void voltarInicio(View v){
        startActivity(new Intent(this, Menu.class));
    }
}
