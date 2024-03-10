package com.example.doan_music.model;

public class AddNgheSi_ThuVien {
    private byte[] hinh;
    private String tensp;

    public AddNgheSi_ThuVien() {
    }

    public AddNgheSi_ThuVien(byte[] hinh, String tensp) {
        this.hinh = hinh;
        this.tensp = tensp;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }
}
