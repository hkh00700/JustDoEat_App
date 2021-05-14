package com.example.empty_can;

public class ListViewItem {
    String name;
    boolean isCheckable;
    private String text;
    boolean check;


    public ListViewItem(String name) {
        this.name = name;
        boolean isCheckable = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
