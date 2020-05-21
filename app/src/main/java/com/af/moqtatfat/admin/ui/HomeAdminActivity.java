package com.af.moqtatfat.admin.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.af.moqtatfat.R;
import com.af.moqtatfat.admin.LoginActivity;
import com.af.moqtatfat.datasource.FireStoreRepository;
import com.af.moqtatfat.datasource.FireStoreService;
import com.af.moqtatfat.ui.WelcomeScreenActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeAdminActivity extends AppCompatActivity {

    private Button logout;
    private TextView open_app;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_home_admin);
        FloatingActionButton add = findViewById(R.id.floatingActionButton);

        bindViews();
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        open_app.setOnClickListener(v -> startActivity(new Intent(this, WelcomeScreenActivity.class)));
        add.setOnClickListener(v -> startActivity(new Intent(this, AddNewQuoteActivity.class)));

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onResume(){
        super.onResume();
        FireStoreService fireStoreService = new FireStoreService(new FireStoreRepository());
        fireStoreService.getDocument(this, recyclerView);
    }

    private void bindViews(){
        logout = findViewById(R.id.logout);
        recyclerView = findViewById(R.id.recyclerView);
        open_app = findViewById(R.id.open_app);
    }


}
