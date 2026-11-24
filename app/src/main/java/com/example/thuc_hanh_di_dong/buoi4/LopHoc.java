package com.example.thuc_hanh_di_dong.buoi4;

import java.io.Serializable;

/**
 * Model Lớp Học (Buổi 4 - Quản lý sinh viên)
 */
@SuppressWarnings("unused")
public class LopHoc implements Serializable {
    private String maLop;
    private String tenLop;
    private int siSo;
    private int soSVHienTai;

    public LopHoc(String maLop, String tenLop, int siSo) {
        this(maLop, tenLop, siSo, 0);
    }

    public LopHoc(String maLop, String tenLop, int siSo, int soSVHienTai) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.siSo = siSo;
        this.soSVHienTai = soSVHienTai;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public int getSiSo() {
        return siSo;
    }

    public void setSiSo(int siSo) {
        this.siSo = siSo;
    }

    public int getSoSVHienTai() {
        return soSVHienTai;
    }

    public void setSoSVHienTai(int soSVHienTai) {
        this.soSVHienTai = soSVHienTai;
    }

    @Override
    public String toString() {
        return "[" + maLop + "] " + tenLop + " - Sĩ số: " + soSVHienTai + "/" + siSo;
    }
}
