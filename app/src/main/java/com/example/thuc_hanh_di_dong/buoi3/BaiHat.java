package com.example.thuc_hanh_di_dong.buoi3;

import java.io.Serializable;

/**
 * Buổi 3 - Bài 17: Model BaiHat
 */
@SuppressWarnings("unused")
public class BaiHat implements Serializable {
    private String ten;
    private String tacGia;
    private String ngayRaDia;
    private String albumMa;

    public BaiHat(String ten, String tacGia, String ngayRaDia, String albumMa) {
        this.ten = ten;
        this.tacGia = tacGia;
        this.ngayRaDia = ngayRaDia;
        this.albumMa = albumMa;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNgayRaDia() {
        return ngayRaDia;
    }

    public void setNgayRaDia(String ngayRaDia) {
        this.ngayRaDia = ngayRaDia;
    }

    public String getAlbumMa() {
        return albumMa;
    }

    public void setAlbumMa(String albumMa) {
        this.albumMa = albumMa;
    }

    @Override
    public String toString() {
        return "🎵 " + ten + " (" + tacGia + ")\n   Ngày: " + ngayRaDia + " | Album: " + albumMa;
    }
}
