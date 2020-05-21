package com.af.moqtatfat.admin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.af.moqtatfat.R;
import com.af.moqtatfat.model.AddNewDocumentRequest;
import com.af.moqtatfat.model.DocumentResponse;
import com.af.moqtatfat.utils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditDeleteQouteActivity extends AppCompatActivity {

    private EditText title, content;
    private TextView delete, save, back, char_num;
    private String doc_id = "";
    private ProgressDialog progressDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_edit_delete_qoute);
        bindView();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("يرجى الانتظار ...");
        progressDialog.create();

        if (getIntent().hasExtra("doc")) {
            DocumentResponse documentResponse = getIntent().getParcelableExtra("doc");
            if (documentResponse != null) {
                title.setText(documentResponse.getTitle() + "");
            }
            if (documentResponse != null) {
                content.setText(documentResponse.getContent() + "");
            }
            if (documentResponse != null) {
                doc_id = documentResponse.getDoc_id();
            }
        }

        content.addTextChangedListener(new TextWatcher() {
            @SuppressLint("SetTextI18n")
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                char_num.setText(content.length() + "" + "/" + "500");
                if (content.length() > 500) {
                    char_num.setTextColor(getResources().getColor(R.color.red));
                    save.setClickable(false);
                    save.setEnabled(false);
                } else {
                    char_num.setTextColor(getResources().getColor(R.color.black));
                    save.setClickable(true);
                    save.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        char_num.setText(content.length() + "" + "/" + "500");

        back.setOnClickListener(v -> finish());
        save.setOnClickListener(v -> updateDocument(db, doc_id));

        delete.setOnClickListener(v -> {

            if (!progressDialog.isShowing()){
                progressDialog.show();
            }

            db.collection(Constants.COLLECTION_NAME).document(doc_id).delete().
                    addOnSuccessListener(aVoid -> {
                        if (!progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        finish();
                    }).
                    addOnFailureListener(e ->{
                        if (!progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                            Toast.makeText(EditDeleteQouteActivity.this,
                                    "there something error please try again.!", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void bindView(){
        title = findViewById(R.id.title_quote);
        content = findViewById(R.id.content_edit_text);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);
        delete = findViewById(R.id.delete);
        char_num = findViewById(R.id.char_num);
    }

    @SuppressLint("SetTextI18n")
    private void updateDocument(FirebaseFirestore db, String doc_id){

        if (!progressDialog.isShowing()){
            progressDialog.show();
        }

        char_num.setText(content.length() + ""+"/"+"500");
        if (content.length() > 500){

            char_num.setTextColor(getResources().getColor(R.color.red));
            save.setClickable(false);
            save.setEnabled(false);

        } else {

            if (title.getText().toString().isEmpty()) {
                Toast.makeText(this, "title can not be empty", Toast.LENGTH_SHORT).show();
            } else if (content.getText().toString().isEmpty()) {
                Toast.makeText(this, "content can not be empty", Toast.LENGTH_SHORT).show();
            } else {

                AddNewDocumentRequest addNewDocumentRequest = new AddNewDocumentRequest();

                addNewDocumentRequest.setTitle(title.getText().toString());
                addNewDocumentRequest.setContent(content.getText().toString());
                addNewDocumentRequest.setCreated_at(new Date() + "");

                Map<String, Object> map = new HashMap<>();

                map.put("content", addNewDocumentRequest.getContent());
                map.put("title", addNewDocumentRequest.getTitle());
                map.put("created_at", addNewDocumentRequest.getCreated_at());

                db.collection(Constants.COLLECTION_NAME).document(doc_id).update(map).
                        addOnSuccessListener(documentReference -> {
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Toast.makeText(this, "Successful Update.!", Toast.LENGTH_LONG).show();
                            finish();
                        })
                        .addOnFailureListener(e ->{
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                                Toast.makeText(this, "There something error please try again.!", Toast.LENGTH_SHORT).show();
                        });
            }
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}
