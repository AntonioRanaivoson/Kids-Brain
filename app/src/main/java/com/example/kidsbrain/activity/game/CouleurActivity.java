package com.example.kidsbrain.activity.game;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.kidsbrain.R;
import com.example.kidsbrain.model.Couleur;

import java.util.List;
import java.util.Random;


public class CouleurActivity extends AppCompatActivity {
    private TextView txtCouleur;
    private TextView txtMarina;
    private Button btnReessayer;
    private int indiceCouleurMarina;
    private List<Couleur> couleurs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couleur);
        init();
    }
    private void init(){
        this.txtCouleur = (TextView)findViewById(R.id.txtCouleur);
        this.txtMarina = (TextView)findViewById(R.id.txtMarina);
        txtMarina.setText("");
        initColors();
        btnReessayer = (Button)findViewById(R.id.btnReessayerCouleur);
        btnReessayer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                init();
            }
        });
    }

    private void initColors(){
        Random random = new Random();
        couleurs = Couleur.getCouleurs(this, 4);
        indiceCouleurMarina = random.nextInt(couleurs.size());
        for(int i =0; i < couleurs.size(); i++){
            initColorButton(i);
        }
        txtCouleur.setText(couleurs.get(indiceCouleurMarina).getNomCouleur());
    }

    private void initColorButton(int indice){
        int idBouton = getResources().getIdentifier("btnCouleur"+(indice+1), "id", this.getPackageName());
        Button bouton = (Button)findViewById(idBouton);
        Couleur couleur = couleurs.get(indice);
        bouton.setBackgroundColor(couleur.getColor());
        bouton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(indice == indiceCouleurMarina){
                    txtMarina.setText("BONNE REPONSE");
                    txtMarina.setTextColor(Color.parseColor("#09FD21"));
                    Handler handler = new Handler();
                    final Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            init();
                        }
                    };
                    handler.postDelayed(r,1000);
                }
                else{
                    txtMarina.setText("MAUVAISE REPONSE");
                    txtMarina.setTextColor(Color.parseColor("#F82E03"));
                    Handler handler = new Handler();
                    final Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            txtMarina.setText("");
                        }
                    };
                    handler.postDelayed(r,1000);
                }
            }
        });
    }

    public TextView getTxtCouleur() {
        return txtCouleur;
    }

    public void setTxtCouleur(TextView txtCouleur) {
        this.txtCouleur = txtCouleur;
    }

    public TextView getTxtMarina() {
        return txtMarina;
    }

    public void setTxtMarina(TextView txtMarina) {
        this.txtMarina = txtMarina;
    }

    public int getIndiceCouleurMarina() {
        return indiceCouleurMarina;
    }

    public void setIndiceCouleurMarina(int indiceCouleurMarina) {
        this.indiceCouleurMarina = indiceCouleurMarina;
    }
}