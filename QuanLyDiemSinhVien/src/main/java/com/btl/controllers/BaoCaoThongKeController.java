package com.btl.controllers;

import com.btl.pojo.LopHoc;
import com.btl.repository.DiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class BaoCaoThongKeController {

    @Autowired
    private DiemRepository diemRepository;

@GetMapping("/bctk")
public String showStatistics(@RequestParam(value = "monHocId", required = false) Integer monHocId,
                             @RequestParam(value = "hocKyId", required = false) Integer hocKyId,
                             Model model) {
    // Load all subjects and semesters for the dropdown
    model.addAttribute("monHocs", diemRepository.getAllMonHocs());
    model.addAttribute("hocKys", diemRepository.getAllHocKys());

    if (monHocId != null && monHocId > 0 && hocKyId != null && hocKyId > 0) {
        // Get all classes
        List<LopHoc> allClasses = diemRepository.getAllLopHocs();

        // Get highest average scores for each class
        List<Map.Entry<String, Double>> lopHocScores = allClasses.stream()
            .map(lopHoc -> {
                List<Object[]> averageScores = diemRepository.getAllAverageScores(monHocId, lopHoc.getId(), hocKyId);
                Double maxAverageScore = averageScores.stream()
                    .map(row -> (Double) row[2])  // Ensure row[2] contains the average score
                    .max(Double::compare)
                    .orElse(0.0);
                return Map.entry(lopHoc.getName(), maxAverageScore);
            })
            .collect(Collectors.toList());

        // Send data to the view
        model.addAttribute("lopHocScores", lopHocScores);
        model.addAttribute("selectedMonHocId", monHocId);
        model.addAttribute("selectedHocKyId", hocKyId);
    }

    return "BaoCaoThongKe";
}


}
