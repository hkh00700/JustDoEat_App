package com.example.empty_can.DTO;

import java.io.Serializable;

public class MemberReviewDTO implements Serializable {
    private int no;
    private String title;
    private String content;
    private String photo_path;
    private String id;

    public MemberReviewDTO(int no, String title, String content, String photo_path, String id) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.photo_path = photo_path;
        this.id = id;
    }

    public MemberReviewDTO(String title, String content, String photo_path, int no, String id) {
        this.title = title;
        this.content = content;
        this.photo_path = photo_path;
        this.no = no;
        this.id = id;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

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

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
