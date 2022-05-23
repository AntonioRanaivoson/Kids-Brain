package com.example.kidsbrain.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inscription extends AppCompatActivity {
    protected EditText nom;
    protected EditText prenom;
    protected EditText mail;
    protected EditText mdp;
    protected Button enregister;
    private User user;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        getSupportActionBar().hide();
        enregister = (Button) findViewById(R.id.btnenregistrer);
        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        mail = (EditText) findViewById(R.id.mail);
        mdp = (EditText) findViewById(R.id.mdp);
        sharedPreferences= this.getSharedPreferences("userIdentity", Context.MODE_PRIVATE);
        /**
         * Boutton fonction Inscription
         */
        enregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(nom)  && validate(mail) && validate(prenom) && validate(mdp)) {
                    signUp();
                }
            }
        });
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
     * inscription
     */
    public void signUp(){
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Inscription.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Enregistrement"); // set message
        progressDialog.show(); // show progress dialog
        User use= new  User(prenom.getText().toString(), nom.getText().toString(), mail.getText().toString(), mdp.getText().toString());
        UserService userservice = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);
        userservice.signUp(use).enqueue(
                new Callback<AuthenticationResponse>() {
                    @Override
                    public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                        if(response.body()!= null) {
                            user = response.body().getUser();
                            doSave(progressDialog,response.body().getToken(),user.getLogin());
                            Intent intent=new Intent(Inscription.this,Homes.class);
                            startActivity(intent);
                        }else {
                            progressDialog.dismiss(); //dismiss progress dialog
                            Toast.makeText(Inscription.this, "Email déjà utilisé", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                        // if error occurs in network transaction then we can get the error in this method.
                        Toast.makeText(Inscription.this, "Email déjà utilisé", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss(); //dismiss progress dialog
                    }
                });
    }
    /**
     * Fonction save preference
     */
    public void doSave(ProgressDialog progressDialog,String token,String login)  {
        UserService userservice = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);
        userservice.getUserConnected(login,"Bearer " + token).enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Intent intent=new Intent(Inscription.this,Homes.class);
                        startActivity(intent);
                        startActivity(intent);
                        User us = response.body();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.putString("login", us.getLogin());
                        editor.putString("prenom", us.getFirstName());
                        editor.putString("nom", us.getLastName());
                        editor.putString("mdp", us.getPassword());
                        editor.apply();
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Inscription.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }
}