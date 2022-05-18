package com.example.kidsbrain.activity.game;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kidsbrain.R;

import java.util.Random;

public class AdditionGame extends AppCompatActivity {
    TextView tvNum1, tvNum2, tvAns, tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition_game);

        tvNum1 = findViewById(R.id.tv_num_1);
        tvNum2 = findViewById(R.id.tv_num_2);
        tvAns = findViewById(R.id.tv_ans);
        tvResult = findViewById(R.id.tv_result);

        run_reset();
    }

    void run_reset(){
        Random myRandom = new Random();

        int num1 = myRandom.nextInt(10);
        int num2 = myRandom.nextInt(10);

        tvNum1.setText(""+num1);
        tvNum2.setText(""+num2);

        tvAns.setText("");
        tvResult.setText("");
    }

    public void clear(View view) {
        run_reset();
    }

    public void submit(View view) {
        int num1 = Integer.parseInt(tvNum1.getText().toString());
        int num2 = Integer.parseInt(tvNum2.getText().toString());
        int ans = num1 + num2;

        int get_user_ans = Integer.parseInt(tvAns.getText().toString());

        if (ans == get_user_ans){
            tvResult.setText("CORRECTE!!");
            run_reset();
            tvResult.setText("CORRECTE!!");
        }
        else {
            tvResult.setText("INCORRECTE!!");
        }
    }
}