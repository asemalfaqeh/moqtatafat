package com.af.moqtatfat.datasource;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.af.moqtatfat.admin.ui.HomeAdminAdapter;
import com.af.moqtatfat.model.DocumentResponse;
import com.af.moqtatfat.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class FireStoreRepository {

    void getDocumentRepo(Context context, RecyclerView recyclerView){

            ArrayList<DocumentResponse> documentResponseArrayList = new ArrayList<>();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(Constants.COLLECTION_NAME).orderBy("created_at", Query.Direction.ASCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                                    Map<String, Object> map = document.getData();

                                    DocumentResponse documentResponse = new DocumentResponse();
                                    documentResponse.setTitle(Objects.requireNonNull(map.get("title")).toString());
                                    documentResponse.setContent(Objects.requireNonNull(map.get("content")).toString());
                                    documentResponse.setDoc_id(document.getId());

                                    documentResponseArrayList.add(documentResponse);
                                    initDocument(context, recyclerView, documentResponseArrayList);
                                }
                            } else {
                                Log.d(getClass().getSimpleName(), "Error getting documents: ", task.getException());
                            }
                        }
                    });
    }

    private void initDocument(Context context, RecyclerView recyclerView, ArrayList<DocumentResponse> documentResponseArrayList){

        HomeAdminAdapter homeAdminAdapter = new HomeAdminAdapter(context, documentResponseArrayList);
        recyclerView.setAdapter(homeAdminAdapter);
        StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(st);

    }

}
