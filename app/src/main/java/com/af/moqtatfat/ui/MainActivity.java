package com.af.moqtatfat.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.af.moqtatfat.R;
import com.af.moqtatfat.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ViewPager myViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewPager = findViewById(R.id.pager);

        List<String> listDate = new ArrayList<>();

        listDate.add(getResources().getString(R.string.text));
        listDate.add(getResources().getString(R.string.text));
        listDate.add(getResources().getString(R.string.text));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(),listDate);

        // Get ViewPager and Set Adapter
        myViewPager = findViewById(R.id.pager);
        myViewPager.setAdapter(viewPagerAdapter);
        // Create an object of page transformer
        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
        // Enable / Disable scaling while flipping. If true, then next page will scale in (zoom in). By default, its true.
        bookFlipPageTransformer.setEnableScale(true);
        // The amount of scale the page will zoom. By default, its 5 percent.
        bookFlipPageTransformer.setScaleAmountPercent(10f);
        // Assign the page transformer to the ViewPager.
        myViewPager.setPageTransformer(true, bookFlipPageTransformer);
        myViewPager.setAdapter(new ViewPagerAdapter(this, listDate));

        getDocuments();

    }

    private void getDocuments(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Constants.COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(getClass().getSimpleName(), document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(getClass().getSimpleName(), "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

}
