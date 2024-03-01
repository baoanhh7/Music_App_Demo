package com.example.doan_music.model;

import java.io.Serializable;

public class ThuVien implements Serializable {
    private  int hinh;
    private  String tensp;
    private String noidung;

    public ThuVien() {
    }

    public ThuVien(int hinh, String tensp, String noidung) {
        this.hinh = hinh;
        this.tensp = tensp;
        this.noidung = noidung;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}

