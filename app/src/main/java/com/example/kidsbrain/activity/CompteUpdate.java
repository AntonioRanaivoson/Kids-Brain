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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.kidsbrain.R;
import com.example.kidsbrain.activity.fragment.CompteFragment;
import com.example.kidsbrain.instance.RetrofitClientInstance;
import com.example.kidsbrain.model.User;
import com.example.kidsbrain.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompteUpdate extends AppCompatActivity {
    protected EditText nom;
    protected EditText prenom;
    protected EditText mail;
    protected EditText mdp;
    protected Button enregister;
    private User user;
    private SharedPreferences sharedPreferences;
    String token,nomS,prenomS,emailS,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_update);
        getSupportActionBar().hide();
        sharedPreferences = this.getSharedPreferences("userIdentity", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        nomS = sharedPreferences.getString("nom","");
        prenomS = sharedPreferences.getString("prenom","");
        emailS = sharedPreferences.getString("login","");
        password = sharedPreferences.getString("mdp","");
        enregister = (Button) findViewById(R.id.btnenregistrer);
        nom = (EditText) findViewById(R.id.nom);
        nom.setText(nomS, TextView.BufferType.EDITABLE);
        prenom = (EditText) findViewById(R.id.prenom);
        prenom.setText(prenomS, TextView.BufferType.EDITABLE);
        mail = (EditText) findViewById(R.id.mail);
        mail.setText(emailS, TextView.BufferType.EDITABLE);
        mdp = (EditText) findViewById(R.id.mdp);
        /**
         * Boutton fonction CompteUpdate
         */
        enregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(nom)  && validate(mail) && validate(prenom) ) {
                    modify(token);
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
    public void modify(String token){
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(CompteUpdate.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Enregistrement"); // set message
        progressDialog.show(); // show progress dialog
        String newMdp = mdp.getText().toString();
        if(newMdp.compareTo("")!=0){
            newMdp = "notcrypted-"+newMdp;
        }else{
            newMdp = "crypted-"+password;
        }
        Log.i("mdp",newMdp);
        User use= new  User(prenom.getText().toString(), nom.getText().toString(), mail.getText().toString(),newMdp);
        UserService userservice = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);
        userservice.modify(use,"Bearer " + token).enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        progressDialog.dismiss(); //dismiss progress dialog
                        if(response.body()!= null) {
                            user = response.body();
                            doSave(user);
                            Toast.makeText(CompteUpdate.this, "Profil Modifié", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CompteUpdate.this, Homes.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(CompteUpdate.this, "Une erreur s'et produit", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(CompteUpdate.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss(); //dismiss progress dialog
                    }
                });
    }
    /**
     * Fonction save preference
     */
    public void doSave(User use)  {
        SharedPreferences sharedPreferences= this.getSharedPreferences("userIdentity", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", use.getLogin());
        editor.putString("prenom", use.getFirstName());
        editor.putString("nom", use.getLastName());
        editor.putString("mdp", use.getPassword());
        editor.apply();

    }


}
