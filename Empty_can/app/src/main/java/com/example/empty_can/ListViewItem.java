package com.example.empty_can;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class ListViewItem implements Parcelable {
    public String name;
    boolean isCheckable;

    public ListViewItem(String name, boolean check) {
        this.name = name;
        this.isCheckable = check;
    }

    protected ListViewItem(Parcel in) {
        name = in.readString();
        isCheckable = in.readByte() != 0;
    }

    public static final Creator<ListViewItem> CREATOR = new Creator<ListViewItem>() {
        @Override
        public ListViewItem createFromParcel(Parcel in) {
            return new ListViewItem(in);
        }

        @Override
        public ListViewItem[] newArray(int size) {
            return new ListViewItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsCheckable() {
        return isCheckable;
    }

    public void setIsCheckable(boolean isCheckable) {
        this.isCheckable = isCheckable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
