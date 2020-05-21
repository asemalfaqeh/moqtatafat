package com.af.moqtatfat.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.af.moqtatfat.R;
import com.af.moqtatfat.model.DocumentResponse;
import com.af.moqtatfat.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ViewPager myViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        // Get ViewPager and Set Adapter
        myViewPager = findViewById(R.id.pager);
        getDocuments();

    }

    private void getDocuments(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.COLLECTION_NAME).orderBy("created_at", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<DocumentResponse> documentResponses = new ArrayList<>();

                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {


                                Map<String, Object> map = document.getData();

                                DocumentResponse documentResponse = new DocumentResponse();
                                documentResponse.setTitle(Objects.requireNonNull(map.get("title")).toString());
                                documentResponse.setContent(Objects.requireNonNull(map.get("content")).toString());
                                documentResponses.add(documentResponse);

                            }

                            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(),documentResponses);

                            // Create an object of page transformer
                            BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
                            // Enable / Disable scaling while flipping. If true, then next page will scale in (zoom in). By default, its true.
                            bookFlipPageTransformer.setEnableScale(true);
                            // The amount of scale the page will zoom. By default, its 5 percent.
                            bookFlipPageTransformer.setScaleAmountPercent(10f);
                            // Assign the page transformer to the ViewPager.
                            myViewPager.setPageTransformer(true, bookFlipPageTransformer);
                            myViewPager.setAdapter(viewPagerAdapter);

                        } else {
                            Log.d(getClass().getSimpleName(), "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
