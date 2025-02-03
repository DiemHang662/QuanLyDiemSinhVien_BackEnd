package com.btl.controllers;

import com.btl.pojo.Khoa;
import com.btl.pojo.LopHoc;
import com.btl.pojo.NganhDaoTao;
import com.btl.pojo.SinhVien;
import com.btl.repository.SinhVienRepository;
import com.btl.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class SinhVienController {

    private final SinhVienService sinhVienService;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    public SinhVienController(SinhVienService sinhVienService) {
        this.sinhVienService = sinhVienService;
    }

    @GetMapping("/dssv")
    public String listSinhVien(
            @RequestParam(value = "khoaId", defaultValue = "-1") int khoaId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "7") int pageSize,
            Model model) {

        if (page < 1) {
            page = 1;
        }

        List<SinhVien> sinhVienList;
        int totalItems;

        // Handle filtering by Khoa
        if (khoaId == -1) {
            sinhVienList = sinhVienService.findAll(page, pageSize);
            totalItems = sinhVienService.countAll();
        } else {
            sinhVienList = sinhVienService.findByKhoaId(khoaId, page, pageSize);
            totalItems = sinhVienService.countByKhoaId(khoaId);
        }

        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        if (page > totalPages) {
            page = totalPages;
            if (khoaId == -1) {
                sinhVienList = sinhVienService.findAll(page, pageSize);
            } else {
                sinhVienList = sinhVienService.findByKhoaId(khoaId, page, pageSize);
            }
        }

        model.addAttribute("sinhVienList", sinhVienList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("selectedKhoaId", khoaId);

        // Retrieve list of Khoa for the filter dropdown
        List<Khoa> khoaList = sinhVienService.getAllKhoas();
        model.addAttribute("khoaList", khoaList);

        return "DanhSachSinhVien"; // Return the JSP view name
    }

    @GetMapping("/chitietsinhvien")
    public String viewSinhVienDetail(@RequestParam("id") int id, Model model) {
        SinhVien sinhVien = sinhVienService.getSinhVienById(id);
        if (sinhVien != null) {
            model.addAttribute("sinhVien", sinhVien);
            return "ChiTietSinhVien";
        } else {
            return "error";
        }
    }

    @PostMapping("/{id}/upload-avatar")
    public String uploadAvatar(@PathVariable("id") int sinhVienId, @RequestParam("file") MultipartFile file) {
        String avatarUrl = sinhVienService.uploadAvatar(file, sinhVienId);
        if (avatarUrl != null) {
            return "redirect:/sinhvien/" + sinhVienId; // Redirect đến trang chi tiết sinh viên
        }
        return "error"; // Xử lý lỗi nếu cần
    }

    @GetMapping("/search")
    public String searchStudent(@RequestParam("searchTerm") String searchTerm, Model model) {
        List<SinhVien> sinhVienList = sinhVienService.searchSinhVienByTerm(searchTerm);
        model.addAttribute("sinhVienList", sinhVienList);
        model.addAttribute("searchTerm", searchTerm); // Pass searchTerm to JSP
        return "DanhSachSinhVien"; // Trả về trang JSP hiển thị danh sách sinh viên
    }

    @GetMapping("/sinhvien/{id}/saveOrUpdate")
    public String showForm(@PathVariable("id") Integer id, Model model) {
        SinhVien sinhVien = (id != null) ? sinhVienService.getSinhVienById(id) : new SinhVien();
        List<LopHoc> lopHocs = sinhVienService.getAllLopHocs();
        List<Khoa> khoas = sinhVienService.getAllKhoas();
        List<NganhDaoTao> nganhDaoTaos = sinhVienService.getAllNganhDaoTaos();

        model.addAttribute("sinhVien", sinhVien);
        model.addAttribute("lopHocs", lopHocs);
        model.addAttribute("khoas", khoas);
        model.addAttribute("nganhDaoTaos", nganhDaoTaos);

        return "SuaSinhVien";
    }

    @PostMapping("/sinhvien/{id}/saveOrUpdate")
    public String saveOrUpdateSinhVien(@PathVariable("id") Integer id,
            @ModelAttribute("sinhVien") SinhVien sinhVien,
            BindingResult result,
            Model model) {

        sinhVienService.saveOrUpdate(sinhVien);
        return "redirect:/dssv";
    }

    @GetMapping("/sinhvien/form")
    public String showAddSinhVienForm(Model model) {
        SinhVien sinhVien = new SinhVien(); // Create an empty SinhVien object for the form
        List<LopHoc> lopHocs = sinhVienService.getAllLopHocs();
        List<Khoa> khoas = sinhVienService.getAllKhoas();
        List<NganhDaoTao> nganhDaoTaos = sinhVienService.getAllNganhDaoTaos();

        model.addAttribute("sinhVien", sinhVien);
        model.addAttribute("lopHocs", lopHocs);
        model.addAttribute("khoas", khoas);
        model.addAttribute("nganhDaoTaos", nganhDaoTaos);

        return "ThemSinhVien"; // Return the view name for adding a student
    }

@PostMapping("/sinhvien/saveOrUpdate")
public String saveOrUpdateSinhVien(@RequestParam(value = "file", required = false) MultipartFile file,
                                    @ModelAttribute("sinhVien") SinhVien sinhVien,
                                    BindingResult result, Model model) {
    System.out.println("Received SinhVien: " + sinhVien);
    System.out.println("GioiTinh: " + sinhVien.getGioiTinh());

    if (result.hasErrors()) {
        System.out.println("Validation errors: " + result.getAllErrors());

        // Lấy danh sách lớp học, khoa, ngành đào tạo để hiển thị lại trên trang
        List<LopHoc> lopHocs = sinhVienService.getAllLopHocs();
        List<Khoa> khoas = sinhVienService.getAllKhoas();
        List<NganhDaoTao> nganhDaoTaos = sinhVienService.getAllNganhDaoTaos();

        model.addAttribute("lopHocs", lopHocs);
        model.addAttribute("khoas", khoas);
        model.addAttribute("nganhDaoTaos", nganhDaoTaos);

        return "ThemSinhVien"; // Giữ người dùng lại trên trang thêm sinh viên
    }

    // Kiểm tra và thiết lập vai trò người dùng mặc định nếu không có
    if (sinhVien.getUserRole() == null || sinhVien.getUserRole().isEmpty()) {
        sinhVien.setUserRole("ROLE_SINHVIEN");
    }

    try {
        sinhVienService.saveOrUpdate(sinhVien);
        System.out.println("SinhVien saved or updated: " + sinhVien);

        // Nếu có ảnh đại diện, xử lý tải ảnh lên
        if (file != null && !file.isEmpty()) {
            System.out.println("Processing avatar file: " + file.getOriginalFilename());
            String avatarUrl = sinhVienService.uploadAvatar(file, sinhVien.getId());
            if (avatarUrl != null) {
                sinhVien.setAvatar(avatarUrl);
                sinhVienService.saveOrUpdate(sinhVien); // Cập nhật thông tin sinh viên với URL ảnh
                System.out.println("Avatar URL updated: " + avatarUrl);
            } else {
                model.addAttribute("errorMessage", "Lỗi khi tải ảnh đại diện. Vui lòng thử lại.");
                return "ThemSinhVien"; // Giữ người dùng lại trên trang thêm sinh viên với thông báo lỗi
            }
        }
        
        System.out.println("Redirecting to /dssv");
        return "redirect:/dssv";

    } catch (Exception e) {
        System.err.println("Exception during save or update: " + e.getMessage());
        model.addAttribute("errorMessage", "Có lỗi xảy ra khi lưu thông tin sinh viên: " + e.getMessage());
        return "ThemSinhVien"; // Giữ người dùng lại trên trang thêm sinh viên với thông báo lỗi
    }
}




    @PostMapping("/sinhvien/{id}/delete")
    public String deleteSinhVien(@PathVariable("id") int id) {
        sinhVienService.deleteById(id);
        return "redirect:/dssv";
    }

    @GetMapping("/dangki")
    public String showRegistrationForm(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        return "DangKi"; // Ensure this matches the JSP file name
    }

    @PostMapping("/dangki")
    public String registerStudent(@RequestParam("file") MultipartFile file,
            @Valid @ModelAttribute("sinhVien") SinhVien sinhVien,
            BindingResult result, Model model) {

        // Kiểm tra lỗi từ BindingResult
        if (result.hasErrors()) {
            return "DangKi";
        }

        // Thiết lập vai trò người dùng mặc định nếu chưa có
        if (sinhVien.getUserRole() == null || sinhVien.getUserRole().isEmpty()) {
            sinhVien.setUserRole("ROLE_SINHVIEN");
        }

        // Kiểm tra tên tài khoản và mật khẩu không được để trống
        if (sinhVien.getUsername() == null || sinhVien.getUsername().isEmpty()
                || sinhVien.getPassword() == null || sinhVien.getPassword().isEmpty()) {
            model.addAttribute("errorMessage", "Tên tài khoản và mật khẩu không được để trống.");
            return "DangKi";
        }

        // Đăng ký sinh viên mới
        boolean isRegistered = sinhVienService.registerSinhVien(sinhVien);
        if (!isRegistered) {
            model.addAttribute("errorMessage", "Tên tài khoản hoặc email đã tồn tại. Vui lòng thử lại.");
            return "DangKi";
        }

        // Xử lý ảnh đại diện nếu có
        if (!file.isEmpty()) {
            try {
                // Tải ảnh lên và lấy URL
                String avatarUrl = sinhVienService.uploadAvatar(file, sinhVien.getId()); // Sử dụng phương thức uploadAvatar với sinhVienId
                if (avatarUrl != null) {
                    sinhVien.setAvatar(avatarUrl);
                    // Cập nhật thông tin sinh viên sau khi tải ảnh
                    sinhVienService.saveOrUpdate(sinhVien); // Cập nhật thông tin sinh viên
                } else {
                    model.addAttribute("errorMessage", "Lỗi khi tải ảnh đại diện. Vui lòng thử lại.");
                    return "DangKi";
                }
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Lỗi khi tải ảnh đại diện: " + e.getMessage());
                return "DangKi";
            }
        }

        return "redirect:/login";
    }

//    @GetMapping("/sinhvien/filterByKhoa")
//    public String filterByKhoa(@RequestParam(value = "khoaId", defaultValue = "-1") int khoaId,
//            @RequestParam(value = "page", defaultValue = "1") int page,
//            @RequestParam(value = "size", defaultValue = "7") int pageSize,
//            Model model) {
//
//        if (page < 1) {
//            page = 1;
//        }
//
//        List<SinhVien> sinhVienList;
//        int totalItems;
//
//        // Handle filtering by Khoa
//        if (khoaId == -1) {
//            sinhVienList = sinhVienService.findAll(page, pageSize);
//            totalItems = sinhVienService.countAll();
//        } else {
//            sinhVienList = sinhVienService.findByKhoaId(khoaId, page, pageSize);
//            totalItems = sinhVienService.countByKhoaId(khoaId);
//        }
//
//        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
//
//        if (page > totalPages) {
//            page = totalPages;
//            if (khoaId == -1) {
//                sinhVienList = sinhVienService.findAll(page, pageSize);
//            } else {
//                sinhVienList = sinhVienService.findByKhoaId(khoaId, page, pageSize);
//            }
//        }
//
//        model.addAttribute("sinhVienList", sinhVienList);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", totalPages);
//        model.addAttribute("pageSize", pageSize);
//        model.addAttribute("selectedKhoaId", khoaId);
//
//        // Retrieve list of Khoa for the filter dropdown
//        List<Khoa> khoaList = sinhVienService.getAllKhoas();
//        model.addAttribute("khoaList", khoaList);
//
//        return "DanhSachSinhVien"; // Return the JSP view name
//    }
}
