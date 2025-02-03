package com.btl.pojo;

import java.io.Serializable;

public class DiemTrungBinh implements Serializable {
    private int sinhVienId;
    private String name;
    private float diemTrungBinh;

    public DiemTrungBinh(int sinhVienId, String name, float diemTrungBinh) {
        this.sinhVienId = sinhVienId;
        this.name = name;
        this.diemTrungBinh = diemTrungBinh;
    }

    // Getters and setters

    public int getSinhVienId() {
        return sinhVienId;
    }

    public void setSinhVienId(int sinhVienId) {
        this.sinhVienId = sinhVienId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(float diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }
}
