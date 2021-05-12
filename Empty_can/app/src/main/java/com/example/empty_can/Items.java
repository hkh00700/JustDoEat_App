package com.example.empty_can;

public class Items {
    boolean checked;
    String ItemString;
    Items(boolean b, String t){
        checked=b;
        ItemString = t;
    }
    public boolean isChecked() {
        return checked;
    }
}
