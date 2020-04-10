package com.af.moqtatfat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DocumentResponse implements Parcelable {

    private String title;
    private String content;
    private String doc_id;

    private DocumentResponse(Parcel in) {
        title = in.readString();
        content = in.readString();
        doc_id = in.readString();
    }

    public DocumentResponse(){}

    public static final Creator<DocumentResponse> CREATOR = new Creator<DocumentResponse>() {
        @Override
        public DocumentResponse createFromParcel(Parcel in) {
            return new DocumentResponse(in);
        }

        @Override
        public DocumentResponse[] newArray(int size) {
            return new DocumentResponse[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(doc_id);
    }
}
