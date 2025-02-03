package com.btl.controllers;

import com.btl.pojo.DienDan;
import com.btl.pojo.GiangVien;
import com.btl.pojo.MonHoc;
import com.btl.pojo.SinhVien;
import com.btl.pojo.TraLoiDienDan;
import com.btl.service.DienDanService;
import com.btl.service.GiangVienService;
import com.btl.service.SinhVienService;
import com.btl.service.TraLoiDienDanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/diendan")
public class DienDanController {

    @Autowired
    private SinhVienService sinhVienService;
    
     @Autowired
    private GiangVienService giangVienService;

    @Autowired
    private DienDanService dienDanService;

    @Autowired
    private TraLoiDienDanService traLoiDienDanService;

    @GetMapping("/{monHocId}")
    public String listDienDan(@PathVariable("monHocId") int monHocId, Model model) {
        List<DienDan> dienDans = dienDanService.getDienDanByMonHoc(monHocId);
        model.addAttribute("dienDans", dienDans);
        model.addAttribute("monHocId", monHocId);
        return "DienDanList";
    }

    @GetMapping("/{dienDanId}/tra-loi")
    public String listTraLoi(@PathVariable("dienDanId") int dienDanId, Model model) {
        List<TraLoiDienDan> traLoiDienDans = traLoiDienDanService.getTraLoiByDienDan(dienDanId);
        model.addAttribute("traLoiDienDans", traLoiDienDans);
        model.addAttribute("dienDanId", dienDanId);
        return "TraLoiDienDanList";
    }

   @PostMapping("/{dienDanId}/tra-loi")
public String addTraLoi(@PathVariable("dienDanId") int dienDanId, @RequestParam("content") String content) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = ((UserDetails) authentication.getPrincipal()).getUsername();

    List<SinhVien> sinhViens = sinhVienService.getNameSinhVien();
    List<GiangVien> giangViens = giangVienService.getNameGiangVien(); 

    SinhVien sinhVien = sinhViens.stream()
            .filter(sv -> sv.getUsername().equals(username))
            .findFirst()
            .orElse(null);

    GiangVien giangVien = giangViens.stream()
            .filter(gv -> gv.getUsername().equals(username))
            .findFirst()
            .orElse(null);

    DienDan dienDan = dienDanService.getDienDanById(dienDanId);

    TraLoiDienDan traLoiDienDan = new TraLoiDienDan();
    traLoiDienDan.setContent(content);
    traLoiDienDan.setDienDan(dienDan);

    if (sinhVien != null) {
        traLoiDienDan.setSinhVien(sinhVien);
    } else if (giangVien != null) {
        traLoiDienDan.setGiangVien(giangVien);
    } else {
        throw new RuntimeException("User not found");
    }

    traLoiDienDanService.addTraLoi(traLoiDienDan);

    return "redirect:/diendan/" + dienDanId + "/tra-loi";
}

 @PostMapping("/{dienDanId}/tra-loi/{traLoiId}/xoa")
public String deleteTraLoi(@PathVariable("dienDanId") int dienDanId, @PathVariable("traLoiId") int traLoiId) {
    try {
        System.out.println("Attempting to delete TraLoiDienDan with ID: " + traLoiId);
        traLoiDienDanService.deleteById(traLoiId);
        System.out.println("Successfully deleted TraLoiDienDan with ID: " + traLoiId);
    } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/diendan/" + dienDanId + "/tra-loi?error=true";
    }
    return "redirect:/diendan/" + dienDanId + "/tra-loi";
}

@GetMapping("/{monHocId}/add")
    public String showAddDienDanForm(@PathVariable("monHocId") int monHocId, Model model) {
        model.addAttribute("monHocId", monHocId);
        model.addAttribute("dienDan", new DienDan()); // Add a blank DienDan object
        return "ThemDienDan"; // JSP page for the form
    }
}
