package com.btl.controllers;

import com.btl.repository.DiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class ApiBaoCaoThongKeController {

    @Autowired
    private DiemRepository diemRepository;

    @PreAuthorize("hasRole('GIANGVIEN')")
    @GetMapping("/api/bctk")
    public ResponseEntity<Map<String, Object>> getStatistics(
            @RequestParam(value = "monHocId", required = false) Integer monHocId,
            @RequestParam(value = "hocKyId", required = false) Integer hocKyId) {

        Map<String, Object> response = new HashMap<>();

        // Get all subjects (MonHocs)
        response.put("monHocs", diemRepository.getAllMonHocs());

        // Check if both monHocId and hocKyId are provided and valid
        if (monHocId != null && monHocId > 0 && hocKyId != null && hocKyId > 0) {
            // Process each class (LopHoc) to calculate max average score
            List<Map<String, Object>> lopHocScores = diemRepository.getAllLopHocs().stream()
                    .map(lopHoc -> {
                        // Calculate max average score for each class
                        Double maxAverageScore = diemRepository.getAllAverageScores(monHocId, lopHoc.getId(), hocKyId)
                                .stream()
                                .map(row -> (Double) row[2])
                                .max(Double::compare)
                                .orElse(0.0);

                        // Prepare result map for each class
                        Map<String, Object> scoreMap = new HashMap<>();
                        scoreMap.put("lopHocName", lopHoc.getName());
                        scoreMap.put("maxAverageScore", maxAverageScore);
                        return scoreMap;
                    })
                    .collect(Collectors.toList());

            // Add the result to the response
            response.put("lopHocScores", lopHocScores);
            response.put("selectedMonHocId", monHocId);
            response.put("selectedHocKyId", hocKyId);
        }

        // Return the response as a ResponseEntity with HTTP status OK
        return ResponseEntity.ok(response);
    }
}
