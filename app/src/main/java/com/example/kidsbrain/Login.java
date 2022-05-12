package com.example.kidsbrain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Button btninscription=findViewById(R.id.btninscription);
        /**
         * Boutton ecran Inscription
         */
        btninscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInscription();
            }
        });

    }

    /**
     * Fonction pour aller dans l'ecran Inscription
     */
    public void openInscription() {
        Intent intent=new Intent(this,Inscription.class);
        startActivity(intent);
    }

}