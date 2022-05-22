package com.example.kidsbrain.activity.game;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kidsbrain.R;

import java.util.Random;

public class SoustractionGame extends AppCompatActivity {

    TextView tvNum1, tvNum2, tvAns, tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soustraction_game);
        tvNum1 = findViewById(R.id.tv_num_1);
        tvNum2 = findViewById(R.id.tv_num_2);
        tvAns = findViewById(R.id.tv_ans);
        tvResult = findViewById(R.id.tv_result);
        run_reset();
        tvAns.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    void run_reset(){
        Random myRandom = new Random();

        int num1 = myRandom.nextInt(10);
        int num2 = getRandom(num1,10);

        tvNum1.setText(""+num2);
        tvNum2.setText(""+num1);

        tvAns.setText("");
        tvResult.setText("");
    }
    public static int getRandom(int from, int to) {
        if (from < to)
            return from + new Random().nextInt(Math.abs(to - from));
        return from - new Random().nextInt(Math.abs(to - from));
    }
    public void clear(View view) {
        run_reset();
    }

    public void submit(View view) {
        int num1 = Integer.parseInt(tvNum1.getText().toString());
        int num2 = Integer.parseInt(tvNum2.getText().toString());
        int ans = num1 - num2;

        if(tvAns.getText().toString().compareTo("")!=0){
            int get_user_ans = Integer.parseInt(tvAns.getText().toString());

            if (ans == get_user_ans){
                tvResult.setText("CORRECTE!!");
                Handler handler = new Handler();
                final Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        run_reset();
                    }
                };
                handler.postDelayed(r,1000);
            }
            else {
                tvResult.setText("INCORRECTE!!");
            }
        }else{
            tvResult.setText("INCORRECTE!!");
        }

    }
}