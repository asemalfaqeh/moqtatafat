package com.af.moqtatfat.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.af.moqtatfat.R;
import com.af.moqtatfat.admin.ui.HomeAdminActivity;
import com.af.moqtatfat.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText username, password;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private TextView open_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser!=null){
           goToHomeUI();
        }

        open_app.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        login.setOnClickListener(v -> {
        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){

            Toast.makeText(this, "Username / Password can not be Empty", Toast.LENGTH_SHORT).show();

        }else {

            disableView();

            auth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    goToHomeUI();
                    enableViews();
                } else {
                    enableViews();
                    Toast.makeText(LoginActivity.this, "Email / Password is Invalid", Toast.LENGTH_SHORT).show();
                }

            }); }
        });

}

    private void bindViews(){
        login = findViewById(R.id.login_btn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        open_app = findViewById(R.id.open_app);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void enableViews(){
        login.setEnabled(true);
        login.setClickable(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void disableView(){
        login.setEnabled(false);
        login.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void goToHomeUI(){
        Intent intent = new Intent(this, HomeAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
