package com.example.kidsbrain.model;

import android.content.Context;
import android.graphics.Color;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Couleur {
    private String nomCouleur;
    private int color;

    public static List<Couleur> listeCouleurs(Context context){
        List<Couleur> couleurs = new ArrayList<>();
        //Color.parseColor("#F82E03")
        couleurs.add( new Couleur("ROUGE", Color.parseColor("#F82E03")));
        couleurs.add( new Couleur("BLEU", Color.parseColor("#0D57F1")));
        couleurs.add( new Couleur("VERT", Color.parseColor("#09FD21")));
        couleurs.add( new Couleur("NOIR", Color.parseColor("#000000")));
        couleurs.add( new Couleur("BLANC", Color.parseColor("#FFFFFF")));
        couleurs.add( new Couleur("JAUNE", Color.parseColor("#E5FC13")));
        couleurs.add( new Couleur("ORANGE", Color.parseColor("#EE8603")));
        couleurs.add( new Couleur("VIOLET", Color.parseColor("#B903EE")));
        couleurs.add( new Couleur("CYAN", Color.parseColor("#08F7E8")));
        return couleurs;
    }

    public static List<Couleur> getCouleurs(Context context, int nombre){
        List<Couleur> resultats = new ArrayList<>();
        List<Couleur> tousCouleurs = Couleur.listeCouleurs(context);
        for(int i=0; i<nombre; i++) {
            Random random = new Random();
            int valeur = random.nextInt(tousCouleurs.size());
            Couleur couleur = tousCouleurs.get(valeur);
            tousCouleurs.remove(valeur);
            resultats.add(couleur);
        }
        return resultats;
    }

    public String getNomCouleur() {
        return nomCouleur;
    }

    public void setNomCouleur(String nomCouleur) {
        this.nomCouleur = nomCouleur;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Couleur(String nomCouleur, int color) {
        this.nomCouleur = nomCouleur;
        this.color = color;
    }
}
