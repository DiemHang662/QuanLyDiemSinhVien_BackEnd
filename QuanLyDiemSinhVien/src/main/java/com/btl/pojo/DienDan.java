package com.btl.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "diendan")
public class DienDan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "dienDan", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TraLoiDienDan> traLoiDienDanList;

    @OneToMany(mappedBy = "dienDan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MonHoc> monHocList;

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

    public List<TraLoiDienDan> getTraLoiDienDanList() {
        return traLoiDienDanList;
    }

    public void setTraLoiDienDanList(List<TraLoiDienDan> traLoiDienDanList) {
        this.traLoiDienDanList = traLoiDienDanList;
    }

    public List<MonHoc> getMonHocList() {
        return monHocList;
    }

    public void setMonHocList(List<MonHoc> monHocList) {
        this.monHocList = monHocList;
    }
}
