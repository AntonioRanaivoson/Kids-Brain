package com.example.kidsbrain.activity.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kidsbrain.R;
import com.example.kidsbrain.activity.game.AdditionGame;
import com.example.kidsbrain.activity.game.CouleurActivity;
import com.example.kidsbrain.activity.game.SoustractionGame;
import com.example.kidsbrain.model.Notification;

public class GameFragment extends Fragment {
    private TextView game_titre,infos;
    Button btnaddition,btnadditioninfo,btnsoustraction,btninfosoustraction,btncouleur,btninfocouleur;
    Dialog mDialog;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("userIdentity", Context.MODE_PRIVATE);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        Notification.createNotification(view.getContext());
        btnaddition=(Button) view.findViewById(R.id.btnaddition);
        btnadditioninfo=(Button) view.findViewById(R.id.btnadditioninfo);
        btnsoustraction=(Button) view.findViewById(R.id.btnsoustraction);
        btninfosoustraction=(Button) view.findViewById(R.id.btninfosoustraction);
        btncouleur=(Button) view.findViewById(R.id.btncouleur);
        btninfocouleur=(Button) view.findViewById(R.id.btninfocouleur);
        mDialog=new Dialog(view.getContext());
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnaddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), AdditionGame.class);
                startActivity(intent);
            }
        });
        btnsoustraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), SoustractionGame.class);
                startActivity(intent);
            }
        });


        btnadditioninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setContentView(R.layout.popup);
                game_titre=mDialog.findViewById(R.id.gametitre);
                infos=mDialog.findViewById(R.id.infos);
                game_titre.setText("Addition");
                infos.setText("Consiste a faire l'addition entre 2 chiffres");
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
            }
        });

        btninfosoustraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setContentView(R.layout.popup);
                game_titre=mDialog.findViewById(R.id.gametitre);
                infos=mDialog.findViewById(R.id.infos);
                game_titre.setText("Soustraction");
                infos.setText("Consiste a faire la soustraction entre 2 chiffres");
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
            }
        });


        btncouleur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), CouleurActivity.class);
                startActivity(intent);
            }
        });



        btninfocouleur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setContentView(R.layout.popup);
                game_titre=mDialog.findViewById(R.id.gametitre);
                infos=mDialog.findViewById(R.id.infos);
                game_titre.setText("Couleur");
                infos.setText("Consiste a choisir la bonne couleur ");
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
            }
        });



    }




}
