package com.btl.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "khoa")
public class Khoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    public Khoa() {}

    public Khoa(Integer id) {
        this.id = id;
    }

    public Khoa(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
