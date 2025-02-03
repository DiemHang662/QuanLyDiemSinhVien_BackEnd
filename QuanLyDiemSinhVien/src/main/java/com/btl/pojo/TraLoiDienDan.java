package com.btl.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "traloidiendan")
public class TraLoiDienDan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDienDan", nullable = false)
    private DienDan dienDan;

    @ManyToOne
    @JoinColumn(name = "idSinhVien", nullable = false)
    private SinhVien sinhVien;
    
    @ManyToOne
    @JoinColumn(name = "idGiangVien", nullable = false)
    private GiangVien giangVien;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DienDan getDienDan() {
        return dienDan;
    }

    public void setDienDan(DienDan dienDan) {
        this.dienDan = dienDan;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }
    
    public GiangVien getGiangVien() {
        return giangVien;
    }

    public void setGiangVien(GiangVien giangVien) {
        this.giangVien = giangVien;
    }

}
