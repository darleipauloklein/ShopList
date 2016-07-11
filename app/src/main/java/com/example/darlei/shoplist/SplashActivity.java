package com.example.darlei.shoplist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // criar handler para troca de tela, é uma thread que espera 3 segundos para chamar outra tela
        Handler h = new  Handler();  //o Handler é uma espécie de agendamento
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(SplashActivity.this,LoginActivity.class);//mensagem ao android para abrir a tela de login
                startActivity(it);//abre a activity login
                finish();//finaliza a atividade splash
            }
        },2000);
    }
}
