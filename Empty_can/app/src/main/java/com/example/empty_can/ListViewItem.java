package com.example.empty_can;

public class ListViewItem {
<<<<<<< HEAD
    String name;
    boolean isCheckable;
=======
    private String text;
    boolean check;
>>>>>>> bfcf786c4477c0784ab5b2cfb5a707da5769df13


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
