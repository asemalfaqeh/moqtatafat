package com.af.moqtatfat.datasource;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;


public class FireStoreService {

    private FireStoreRepository fireStoreRepository;

    public FireStoreService(FireStoreRepository fireStoreRepository) {
        this.fireStoreRepository = fireStoreRepository;
    }

    public void getDocument(Context context, RecyclerView recyclerView){
         fireStoreRepository.getDocumentRepo(context, recyclerView);
    }

}
