package com.af.moqtatfat.admin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.af.moqtatfat.R;
import com.af.moqtatfat.model.AddNewDocumentRequest;
import com.af.moqtatfat.utils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddNewQuoteActivity extends AppCompatActivity {

    EditText title, content;
    TextView back, save, char_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_qoute);
        bindViews();

        save.setOnClickListener(v -> addNewDocument());
        back.setOnClickListener(v -> finish());

        content.addTextChangedListener(new TextWatcher() {
            @SuppressLint("SetTextI18n")
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                char_num.setText(content.length() + ""+"/"+"500");
                if (content.length() > 500){
                    char_num.setTextColor(getResources().getColor(R.color.red));
                    save.setClickable(false);
                    save.setEnabled(false);
                }else {
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

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void addNewDocument(){

        char_num.setText(content.length() + ""+"/"+"500");
        if (content.length() > 500){
            char_num.setTextColor(getResources().getColor(R.color.red));
            save.setClickable(false);
            save.setEnabled(false);
        }else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

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

                db.collection(Constants.COLLECTION_NAME).add(map).
                        addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Successful Added.!", Toast.LENGTH_LONG).show();
                            finish();
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "There something error please try again.!", Toast.LENGTH_SHORT).show());
            }
        }
    }

    private void bindViews(){
        title = findViewById(R.id.title_quote);
        content = findViewById(R.id.content_edit_text);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);
        char_num = findViewById(R.id.char_num);
    }

}
