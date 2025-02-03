package com.btl.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "diem")
public class Diem implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "score", nullable = false)
    private float score;

    @ManyToOne
    @JoinColumn(name = "idSinhVien", nullable = false)
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "idMonHoc", nullable = false)
    private MonHoc monHoc;

    @ManyToOne
    @JoinColumn(name = "idLoaiDiem", nullable = false)
    private LoaiDiem loaiDiem;

    @ManyToOne
    @JoinColumn(name = "idHocKy", nullable = false)
    private HocKy hocKy;

    @ManyToOne
    @JoinColumn(name = "idLopHoc", nullable = false)
    private LopHoc lopHoc;

    public LopHoc getLopHoc() {
        return lopHoc;
    }

    public void setLopHoc(LopHoc lopHoc) {
        this.lopHoc = lopHoc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    public LoaiDiem getLoaiDiem() {
        return loaiDiem;
    }

    public void setLoaiDiem(LoaiDiem loaiDiem) {
        this.loaiDiem = loaiDiem;
    }

    public HocKy getHocKy() {
        return hocKy;
    }

    public void setHocKy(HocKy hocKy) {
        this.hocKy = hocKy;
    }
}
