package com.example.kidsbrain.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kidsbrain.R;
import com.example.kidsbrain.instance.RetrofitClientInstance;
import com.example.kidsbrain.model.AuthenticationResponse;
import com.example.kidsbrain.model.User;
import com.example.kidsbrain.service.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    User user;
    String token;
    EditText login, password;
    Button btninscription, btnconnect;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        btnconnect = (Button) findViewById(R.id.btnconnect);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        btninscription = (Button) findViewById(R.id.btninscription);
        sharedPreferences= this.getSharedPreferences("userIdentity", Context.MODE_PRIVATE);
        /**
         * Boutton ecran Inscription
         */
        btninscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInscription();
            }
        });
        /**
         * Boutton Login
         */
        btnconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // verification de tout les inputs
                if (validate(login)  && validate(password)) {
                    signIn();
                }
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
    /**
     * verification des editText
     */
    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Champs obligatoire");
        editText.requestFocus();
        return false;
    }
    /**
     * Fonction login
     */
    private void signIn() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Chargement"); // set message
        progressDialog.show(); // show progress dialog
        User use= new User(login.getText().toString(),password.getText().toString());
        UserService userservice = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);
        userservice.login(use).enqueue(
                 new Callback<AuthenticationResponse>() {
                     @Override
                     public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {

                         if(response.body()!= null) {
                             user = response.body().getUser();
                             token = response.body().getToken();
                             doSave(progressDialog,token,response.body().getUser().getLogin());
                         }else {
                             Toast.makeText(Login.this, "Login ou mots de passe incorrecte", Toast.LENGTH_SHORT).show();
                         }
                     }
                     @Override
                     public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                         // if error occurs in network transaction then we can get the error in this method.
                         Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_LONG).show();
                         progressDialog.dismiss(); //dismiss progress dialog
                     }
                });

    }
    /**
     * Fonction save preference
     */
    public void doSave(ProgressDialog progressDialog,String token,String login)  {
        UserService userservice = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);
        progressDialog.dismiss();
        userservice.getUserConnected(login,"Bearer " + token).enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Intent intent=new Intent(Login.this,Homes.class);
                        startActivity(intent);
                       //dismiss progress dialog
                        User us = response.body();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.putString("login", us.getLogin());
                        editor.putString("prenom", us.getFirstName());
                        editor.putString("nom", us.getLastName());
                        editor.putString("mdp", us.getPassword());
                        editor.apply();
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                        Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}