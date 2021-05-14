package com.example.empty_can;

public class ListViewItem { 
    private String text;
    private boolean check;

    public ListViewItem(String text, boolean check) {
        this.text = text;
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
