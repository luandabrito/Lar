package com.junior.lar;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


    public void visita (View v) {
        startActivity(new Intent(this, Visita.class));

    }

    public void sobre (View v) {
        startActivity(new Intent(this, Sobre.class));

    }

    public void estatistica (View v) {
        startActivity(new Intent(this, Estatistica.class));

    }
    public void doacao (View v) {
        startActivity(new Intent(this, MenuDoacao.class));

    }
}
