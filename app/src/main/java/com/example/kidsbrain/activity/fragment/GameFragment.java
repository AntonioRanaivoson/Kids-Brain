package com.example.kidsbrain.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kidsbrain.R;
import com.example.kidsbrain.activity.AdditionGame;
import com.example.kidsbrain.activity.Homes;
import com.example.kidsbrain.activity.Login;
import com.example.kidsbrain.activity.MainActivity;

public class GameFragment extends Fragment {
    private TextView game;
    Button btnaddition;
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
        game = view.findViewById(R.id.game_text);
        game.setText(sharedPreferences.getString("login",""));
        btnaddition=(Button) view.findViewById(R.id.btnaddition);
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
    }
}
