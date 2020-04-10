package com.af.moqtatfat.model;

public class AddNewDocumentRequest {

    private String title;
    private String content;
    private String created_at;

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "AddNewDocumentRequest{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
