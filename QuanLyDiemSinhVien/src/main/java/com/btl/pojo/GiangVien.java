package com.btl.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "giangvien")
public class GiangVien implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;
    
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

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnore
    private NguoiDung nguoiDung;

    @OneToMany(mappedBy = "giangVien")
    @JsonIgnore
    private Set<GiangVienLopHoc> giangVienLopHocs = new HashSet<>();
    
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

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public String getUsername() {
        return nguoiDung != null ? nguoiDung.getUsername() : null;
    }

    public String getPassword() {
        return nguoiDung != null ? nguoiDung.getPassword() : null;
    }

    public Set<GiangVienLopHoc> getGiangVienLopHocs() {
        return giangVienLopHocs;
    }

    public void setGiangVienLopHocs(Set<GiangVienLopHoc> giangVienLopHocs) {
        this.giangVienLopHocs = giangVienLopHocs;
    }
}
