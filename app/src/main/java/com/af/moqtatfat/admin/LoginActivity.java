package com.af.moqtatfat.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.af.moqtatfat.R;
import com.af.moqtatfat.admin.ui.HomeAdminActivity;
import com.af.moqtatfat.ui.MainActivity;
import com.af.moqtatfat.ui.WelcomeScreenActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText username, password;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private TextView open_app;
    private static final String TAG = "LoginActivity";

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

        open_app.setOnClickListener(v -> startActivity(new Intent(this, WelcomeScreenActivity.class)));
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

        if (checkPlayServices()){
            Toast.makeText(this, "Available", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Not Available", Toast.LENGTH_SHORT).show();
        }

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

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, 0)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("please download google play services");
                builder.setCancelable(false);
                builder.create();
                builder.show();
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            }
            return false;
        }
        return true;
    }

}
