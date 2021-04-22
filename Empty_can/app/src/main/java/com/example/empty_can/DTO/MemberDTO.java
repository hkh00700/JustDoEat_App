package com.example.empty_can.DTO;

public class MemberDTO {
    String id;
    String pw;
    String name;
    String phone;
    String email;
    String gender;
    String nikname;

    //암호 없이 멤버정보를 가져올때
    public MemberDTO(String id, String name, String phone, String email, String gender, String nikname) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.nikname = nikname;
    }

    //데이터베이스에 멤버정보를 추가할때
    public MemberDTO(String id, String pw, String name, String phone, String email, String gender, String nikname) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.nikname = nikname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNikname() {
        return nikname;
    }

    public void setNikname(String nikname) {
        this.nikname = nikname;
    }
}
