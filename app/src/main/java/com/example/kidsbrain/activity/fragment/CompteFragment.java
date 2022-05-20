package com.example.kidsbrain.activity.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.kidsbrain.R;
import com.example.kidsbrain.activity.CompteUpdate;
import com.example.kidsbrain.activity.Homes;
import com.example.kidsbrain.activity.Login;
import com.example.kidsbrain.instance.RetrofitClientInstance;
import com.example.kidsbrain.model.Video;
import com.example.kidsbrain.service.UserService;
import com.example.kidsbrain.service.VideoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompteFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private Button deconnect,modifier;
    String token,nomS,prenomS,emailS;
    TextView nom_prenom,nom,prenom,email;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("userIdentity", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        nomS = sharedPreferences.getString("nom","");
        prenomS = sharedPreferences.getString("prenom","");
        emailS = sharedPreferences.getString("login","");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compte, container, false);
        nom_prenom = view.findViewById(R.id.nom_prenom);
        nom_prenom.setText(nomS+' '+prenomS);
        nom = view.findViewById(R.id.nom);
        nom.setText(nomS);
        prenom = view.findViewById(R.id.prenom);
        prenom.setText(prenomS);
        email = view.findViewById(R.id.email);
        email.setText(emailS);
        deconnect = (Button) view.findViewById(R.id.btndeconnect);
        deconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        modifier = (Button) view.findViewById(R.id.btnmodifier);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifier();
            }
        });
        return view;
    }

    public void logout(){
        sharedPreferences.edit().clear().commit();
        startActivity(new Intent(getContext(), Login.class));
    }

    public void modifier(){
        startActivity(new Intent(getContext(), CompteUpdate.class));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO){
            Log.i("mode","ligth");
        }else{
            Log.i("mode","dark");
        }
    }
}
