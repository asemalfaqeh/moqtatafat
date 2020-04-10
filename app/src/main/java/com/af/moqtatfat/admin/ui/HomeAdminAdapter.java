package com.af.moqtatfat.admin.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.af.moqtatfat.R;
import com.af.moqtatfat.model.DocumentResponse;
import java.util.ArrayList;

public class HomeAdminAdapter extends RecyclerView.Adapter<HomeAdminAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DocumentResponse> documentResponseArrayList;

    public HomeAdminAdapter(Context context, ArrayList<DocumentResponse> documentResponses){
        this.context = context;
        this.documentResponseArrayList = documentResponses;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, content;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_adapter);
            content = itemView.findViewById(R.id.content_adapter);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_adapter_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DocumentResponse documentResponse = documentResponseArrayList.get(position);

        holder.content.setText(documentResponse.getContent());
        holder.title.setText(documentResponse.getTitle());

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditDeleteQouteActivity.class);
            intent.putExtra("doc", documentResponse);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return documentResponseArrayList.size();
    }


}
