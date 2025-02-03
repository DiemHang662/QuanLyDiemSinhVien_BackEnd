package com.btl.controllers;

import com.btl.pojo.MonHoc;
import com.btl.service.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MonHocController {

    @Autowired
    private MonHocService monHocService;

 @GetMapping("/sinhvien/monhoc")
    public String listMonHocsBySinhVien(@RequestParam("sinhVienId") int sinhVienId, Model model) {
        List<MonHoc> monHocs = monHocService.getMonHocsBySinhVienId(sinhVienId);
        model.addAttribute("monHocs", monHocs);
        return "MonHocSV";
    }

}
