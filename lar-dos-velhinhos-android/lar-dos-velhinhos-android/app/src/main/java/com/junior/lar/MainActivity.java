package com.junior.lar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent tela2 = new Intent(this, Menu.class); // trocar o nome p a proxima tela tela2
        setContentView(R.layout.activity_main);
        Button btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                TextView tLogin = (TextView) findViewById(R.id.tLogin);
                TextView tSenha = (TextView) findViewById(R.id.tSenha);

                String login = tLogin.getText().toString();
                String senha = tSenha.getText().toString();

                if (login.equals("Junior") ||
                        (login.equals("Luanda")) ||
                                (login.equals("Laan")) ||
                                        (login.equals("Will")) ||
                                                (login.equals("Sheila")) ||
                                                        (login.equals("Caio"))

                                                         && senha.equals("123")) {

                    startActivity(tela2);
                    alert("Login realizado com sucesso ");

                } else {
                    alert("Login ou senha incorretos ");

                }

            }
        });
    }

    private void alert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();


    }


}
