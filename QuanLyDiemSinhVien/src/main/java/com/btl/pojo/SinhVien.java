package com.btl.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "sinhvien")
public class SinhVien extends NguoiDung implements Serializable {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "idLopHoc")
    @JsonIgnore
    private LopHoc lopHoc;

    @ManyToOne
    @JoinColumn(name = "idKhoa")
    @JsonIgnore
    private Khoa khoa;

    @ManyToOne
    @JoinColumn(name = "idNganhDaoTao")
    @JsonIgnore
    private NganhDaoTao nganhDaoTao;

    @OneToMany(mappedBy = "sinhVien", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Diem> diems;

    @NotNull
    @Email(message = "Email không đúng định dạng")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@ou\\.edu\\.vn$", message = "Email phải có đuôi @ou.edu.vn")
    @Column(nullable = false, unique = true)
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "ngaySinh", nullable = false)
    private Date ngaySinh;

    @NotNull
    @Size(max = 100)
    @Column(name = "queQuan")
    private String queQuan;

    @NotNull
    @Size(max = 10)
    @Column(nullable = false)
    private String gioiTinh;  

    @Column(name = "avatar")
    private String avatar; 

    public SinhVien() {
    }

    public SinhVien(Integer id) {
        super(id);
    }

    public SinhVien(Integer id, String name, LopHoc lopHoc, Khoa khoa, NganhDaoTao nganhDaoTao, String email, Date ngaySinh, String queQuan, String gioiTinh, String avatar) {
        super(id);
        this.name = name;
        this.lopHoc = lopHoc;
        this.khoa = khoa;
        this.nganhDaoTao = nganhDaoTao;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.avatar = avatar;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LopHoc getLopHoc() {
        return lopHoc;
    }

    public void setLopHoc(LopHoc lopHoc) {
        this.lopHoc = lopHoc;
    }

    public Khoa getKhoa() {
        return khoa;
    }

    public void setKhoa(Khoa khoa) {
        this.khoa = khoa;
    }

    public NganhDaoTao getNganhDaoTao() {
        return nganhDaoTao;
    }

    public void setNganhDaoTao(NganhDaoTao nganhDaoTao) {
        this.nganhDaoTao = nganhDaoTao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Diem> getDiems() {
        return diems;
    }

    public void setDiems(Set<Diem> diems) {
        this.diems = diems;
    }
}
