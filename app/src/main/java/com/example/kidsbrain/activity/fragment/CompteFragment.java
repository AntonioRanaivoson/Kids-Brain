package com.example.kidsbrain.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kidsbrain.R;
import com.example.kidsbrain.activity.Login;

public class CompteFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private Button deconnect;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("userIdentity", Context.MODE_PRIVATE);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compte, container, false);
        deconnect = (Button) view.findViewById(R.id.btndeconnect);
        deconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return view;
    }
    public void logout(){
        sharedPreferences.edit().clear().commit();
        startActivity(new Intent(getContext(), Login.class));
    }
}
