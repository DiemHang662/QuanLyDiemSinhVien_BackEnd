package com.btl.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(GiangVienLopHocId.class)
@Table(name = "giangvien_lophoc")
public class GiangVienLopHoc implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "idGiangVien")
     @JsonIgnore
    private GiangVien giangVien;

    @Id
    @ManyToOne
    @JoinColumn(name = "idLopHoc")
     @JsonIgnore
    private LopHoc lopHoc;

    // Getters and Setters

    public GiangVien getGiangVien() {
        return giangVien;
    }

    public void setGiangVien(GiangVien giangVien) {
        this.giangVien = giangVien;
    }

    public LopHoc getLopHoc() {
        return lopHoc;
    }

    public void setLopHoc(LopHoc lopHoc) {
        this.lopHoc = lopHoc;
    }
}
