package com.example.empty_can.DTO;

public class RestMenuInfoDTO {
    String imgPath;
    String title, address, tel, menu;

    public RestMenuInfoDTO(String imgPath, String title, String address, String tel, String menu) {
        this.imgPath = imgPath;
        this.title = title;
        this.address = address;
        this.tel = tel;
        this.menu = menu;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}
