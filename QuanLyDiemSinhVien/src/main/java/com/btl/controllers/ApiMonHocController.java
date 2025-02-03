package com.btl.controllers;

import com.btl.pojo.MonHoc;
import com.btl.service.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/monhoc")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiMonHocController {

    @Autowired
    private MonHocService monHocService;

    @PreAuthorize("hasRole('GIANGVIEN') or hasRole('SINHVIEN')")
    @GetMapping("/list")
    public ResponseEntity<List<MonHoc>> listMonHocsBySinhVien(@RequestParam("sinhVienId") int sinhVienId) {
        List<MonHoc> monHocs = monHocService.getMonHocsBySinhVienId(sinhVienId);
        return ResponseEntity.ok(monHocs);
    }
}
