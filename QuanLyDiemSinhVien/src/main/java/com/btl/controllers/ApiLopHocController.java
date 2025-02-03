package com.btl.controllers;

import com.btl.pojo.LopHoc;
import com.btl.pojo.GiangVienLopHoc;
import com.btl.pojo.SinhVien;
import com.btl.service.LopHocService;
import com.btl.service.GiangVienLopHocService;
import com.btl.service.GiangVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("/api/lophoc")
public class ApiLopHocController {

    @Autowired
    private LopHocService lopHocService;

    @Autowired
    private GiangVienService giangVienService;

    @Autowired
    private GiangVienLopHocService giangVienLopHocService;

    @PreAuthorize("hasRole('GIANGVIEN')")
    @GetMapping("/list")
    public ResponseEntity<List<LopHoc>> listLopHoc() {
        List<LopHoc> lopHocList = lopHocService.findAll();
        return ResponseEntity.ok(lopHocList);
    }

    @PreAuthorize("hasRole('GIANGVIEN')")
    @GetMapping("/list/{id}")
    public ResponseEntity<LopHocDetails> getLopHocDetails(
            @PathVariable int id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "7") int pageSize) {
        
        LopHoc lopHoc = lopHocService.findById(id);
        if (lopHoc != null) {
            List<SinhVien> sinhVienList = lopHocService.getStudentsByLopHocId(id, page, pageSize);
            List<GiangVienLopHoc> giangVienLopHocList = giangVienLopHocService.findByLopHocId(id);
            LopHocDetails details = new LopHocDetails(lopHoc, sinhVienList, giangVienLopHocList, page, pageSize);
            return ResponseEntity.ok(details);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public static class LopHocDetails {
        private LopHoc lopHoc;
        private List<SinhVien> sinhVienList;
        private List<GiangVienLopHoc> giangVienLopHocList;
        private int currentPage;
        private int pageSize;

        public LopHocDetails(LopHoc lopHoc, List<SinhVien> sinhVienList, List<GiangVienLopHoc> giangVienLopHocList, int currentPage, int pageSize) {
            this.lopHoc = lopHoc;
            this.sinhVienList = sinhVienList;
            this.giangVienLopHocList = giangVienLopHocList;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
        }

        // Getters and setters...

        public LopHoc getLopHoc() {
            return lopHoc;
        }

        public void setLopHoc(LopHoc lopHoc) {
            this.lopHoc = lopHoc;
        }

        public List<SinhVien> getSinhVienList() {
            return sinhVienList;
        }

        public void setSinhVienList(List<SinhVien> sinhVienList) {
            this.sinhVienList = sinhVienList;
        }

        public List<GiangVienLopHoc> getGiangVienLopHocList() {
            return giangVienLopHocList;
        }

        public void setGiangVienLopHocList(List<GiangVienLopHoc> giangVienLopHocList) {
            this.giangVienLopHocList = giangVienLopHocList;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }
}

