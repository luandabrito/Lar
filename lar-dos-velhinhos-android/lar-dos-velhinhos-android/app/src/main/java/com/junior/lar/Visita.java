package com.junior.lar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Visita extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita);
        createNotificationChannel();
    }

    public void limpar (View v){
        EditText editText = findViewById(R.id.editNomeText);
        editText.setText("");
        EditText editMail = findViewById(R.id.editEmailText);
        editMail.setText("");
        EditText editPhone = findViewById(R.id.editTelefoneText);
        editPhone.setText("");
    }

    public static int notificationId = 1;
    private String CHANNEL_ID = "login_app_id";

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificação Visita";
            String description = "Criação de um canal para aplicação";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Log.i("Aula04", "Mensagem Enviada" + CHANNEL_ID);

        }
    }

    public void notificationAction(View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.tartaruga1)
                .setContentTitle("O Lar dos Velhinhos de Campinas agradece!")
                .setContentText("Seu interesse de visita foi enviado com sucesso!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId++, builder.build());


    }

    public void voltar (View v) {
        startActivity(new Intent(this, Menu.class));

    }







}
