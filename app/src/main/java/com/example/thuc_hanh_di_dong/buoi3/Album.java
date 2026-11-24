package com.example.thuc_hanh_di_dong.buoi3;

import java.io.Serializable;

/**
 * Buổi 3 - Bài 17: Model Album
 */
@SuppressWarnings("unused")
public class Album implements Serializable {
    private String ma;
    private String ten;
    private String caSi;

    public Album(String ma, String ten, String caSi) {
        this.ma = ma;
        this.ten = ten;
        this.caSi = caSi;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getCaSi() {
        return caSi;
    }

    public void setCaSi(String caSi) {
        this.caSi = caSi;
    }

    @Override
    public String toString() {
        return "[" + ma + "] " + ten + " - " + caSi;
    }
}
