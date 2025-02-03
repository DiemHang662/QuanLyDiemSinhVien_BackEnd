//package com.btl.controllers;
//
//import com.btl.pojo.DienDan;
//import com.btl.pojo.SinhVien;
//import com.btl.pojo.TraLoiDienDan;
//import com.btl.service.DienDanService;
//import com.btl.service.SinhVienService;
//import com.btl.service.TraLoiDienDanService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/diendan")
//public class ApiDienDanController {
//
//    @Autowired
//    private SinhVienService sinhVienService;
//
//    @Autowired
//    private DienDanService dienDanService;
//
//    @Autowired
//    private TraLoiDienDanService traLoiDienDanService;
//
//    @GetMapping("/{monHocId}")
//    public ResponseEntity<List<DienDan>> listDienDan(@PathVariable("monHocId") int monHocId) {
//        List<DienDan> dienDans = dienDanService.getDienDanByMonHoc(monHocId);
//        return ResponseEntity.ok(dienDans);
//    }
//
//    @GetMapping("/{dienDanId}/tra-loi")
//    public ResponseEntity<List<TraLoiDienDan>> listTraLoi(@PathVariable("dienDanId") int dienDanId) {
//        List<TraLoiDienDan> traLoiDienDans = traLoiDienDanService.getTraLoiByDienDan(dienDanId);
//        return ResponseEntity.ok(traLoiDienDans);
//    }
//
//    @PostMapping("/{dienDanId}/tra-loi")
//    public ResponseEntity<TraLoiDienDan> addTraLoi(@PathVariable("dienDanId") int dienDanId, @RequestParam("content") String content) {
//        // Retrieve the currently logged-in user's username
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//        
//        // Fetch the list of SinhVien
//        List<SinhVien> sinhViens = sinhVienService.getNameSinhVien();
//        
//        // Find the SinhVien object corresponding to the logged-in user
//        SinhVien sinhVien = sinhViens.stream()
//                .filter(sv -> sv.getUsername().equals(username))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("SinhVien not found for username: " + username));
//        
//        // Fetch DienDan entity based on ID
//        DienDan dienDan = dienDanService.getDienDanById(dienDanId);
//        
//        // Create and save TraLoiDienDan
//        TraLoiDienDan traLoiDienDan = new TraLoiDienDan();
//        traLoiDienDan.setContent(content);
//        traLoiDienDan.setDienDan(dienDan);
//        traLoiDienDan.setSinhVien(sinhVien);
//        TraLoiDienDan savedTraLoiDienDan = traLoiDienDanService.addTraLoi(traLoiDienDan);
//        
//        return ResponseEntity.ok(savedTraLoiDienDan);
//    }
//}
