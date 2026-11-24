package com.example.thuc_hanh_di_dong.buoi4;

import java.io.Serializable;

/**
 * Model Sinh Viên (Buổi 4 - Quản lý sinh viên)
 */
@SuppressWarnings("unused")
public class SinhVien implements Serializable {
    private String maSV;
    private String tenSV;
    private String gioiTinh;
    private String ngaySinh;
    private String diaChi;
    private String maLop;
    private String soDienThoai;
    private String email;
    private double diemTB;

    public SinhVien(String maSV, String tenSV, String gioiTinh, String ngaySinh, String diaChi, String maLop) {
        this(maSV, tenSV, gioiTinh, ngaySinh, diaChi, maLop, "", "", 0.0);
    }

    public SinhVien(String maSV, String tenSV, String gioiTinh, String ngaySinh, String diaChi,
                    String maLop, String soDienThoai, String email, double diemTB) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.maLop = maLop;
        this.soDienThoai = soDienThoai != null ? soDienThoai : "";
        this.email = email != null ? email : "";
        this.diemTB = diemTB;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai != null ? soDienThoai : "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email : "";
    }

    public double getDiemTB() {
        return diemTB;
    }

    public void setDiemTB(double diemTB) {
        this.diemTB = diemTB;
    }

    public String getXepLoai() {
        if (diemTB >= 9.0) {
            return "Xuất sắc";
        } else if (diemTB >= 8.0) {
            return "Giỏi";
        } else if (diemTB >= 6.5) {
            return "Khá";
        } else if (diemTB >= 5.0) {
            return "Trung bình";
        } else {
            return "Yếu";
        }
    }

    @Override
    public String toString() {
        return "[" + maSV + "] " + tenSV + " | " + gioiTinh + " | " + ngaySinh
                + "\nLớp: " + maLop + " | Điểm TB: " + diemTB + " (" + getXepLoai() + ")"
                + "\nSĐT: " + soDienThoai + " | Địa chỉ: " + diaChi;
    }
}
