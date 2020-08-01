package com.example.sqlite_data;

public class Models_ListView {
    private int id;
    private String ten;

    public Models_ListView(int id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public Models_ListView() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
