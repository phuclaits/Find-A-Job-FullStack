package com.doan.AppTuyenDung.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import com.doan.AppTuyenDung.Repositories.PackageService;
import java.time.LocalDate;
@RestController
@RequestMapping()
public class PackageController {
    @Autowired
    private PackageService packageService;


    @GetMapping("/public/get-statistical-cv")
    public ResponseEntity<Map<String, Object>> getStatisticalPackage(
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset) {
        

        // Xử lý fromDate và toDate nếu null, sử dụng ngày hiện tại nếu không được cung cấp
        if (fromDate == null) {
            fromDate = LocalDate.now().withDayOfMonth(1).toString();  // Ngày đầu tháng
        }
        if (toDate == null) {
            toDate = LocalDate.now().toString();  // Ngày hiện tại
        }
        
        Map<String, Object> result = packageService.getStatisticalPackage(fromDate, toDate, limit, offset);

        if (result.containsKey("errCode") && (int) result.get("errCode") == 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        return ResponseEntity.ok(result);
    }

}
