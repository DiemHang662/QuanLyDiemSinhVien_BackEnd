package com.btl.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "monhoc")
public class MonHoc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "idDienDan")

    private DienDan dienDan;

    @OneToMany(mappedBy = "monHoc", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Diem> diems;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DienDan getDienDan() {
        return dienDan;
    }

    public void setDienDan(DienDan dienDan) {
        this.dienDan = dienDan;
    }

    public List<Diem> getDiems() {
        return diems;
    }

    public void setDiems(List<Diem> diems) {
        this.diems = diems;
    }
}
