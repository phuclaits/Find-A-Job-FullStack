package com.doan.AppTuyenDung.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.doan.AppTuyenDung.AWS.S3Util;
import com.doan.AppTuyenDung.entity.Company;
// import com.doan.AppTuyenDung.Repositories.CompanyRepository;
// import com.doan.AppTuyenDung.Repositories.ImagesCompanyRepository;
// // import com.doan.AppTuyenDung.entity.Company;
// // import com.doan.AppTuyenDung.entity.ImagesCompany;
import com.doan.AppTuyenDung.Repositories.CompanyRepository;

import lombok.RequiredArgsConstructor;

// import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/images")
// @RequiredArgsConstructor
public class ImagesCompanyController {
    
    // @Autowired
    // ImagesCompanyRepository imagesCompanyRepository;

    @Autowired
    CompanyRepository companyRepository;

    @PostMapping("/file-upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile multipart,
                                                   @RequestParam("company_id") Integer companyId) {
        String fileName = multipart.getOriginalFilename();
        String message;

        try {
            String newFileName = S3Util.urlFolder + "default-images-demo" + fileName.substring(fileName.lastIndexOf('.'));
            S3Util.uploadFile(newFileName, multipart.getInputStream());

            // get  URL của file đã upload
            String fileUrl = "https://s3-" + S3Util.region + ".amazonaws.com/" + S3Util.bucketName + "/" + newFileName;
            
            // find id company
            // Optional<Company> optionalCompany = companyRepository.findById(companyId);
            // if (!optionalCompany.isPresent()) {
            //     return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
            // }
            // Company company = optionalCompany.get();
            
            
            // ImagesCompany imagesCompany = new ImagesCompany();
            // imagesCompany.setCompany(company);
            // imagesCompany.setAddress_image(fileUrl);
            // imagesCompany.setDate_created(new Date());

            // imagesCompanyRepository.save(imagesCompany);

            message = "Your file has been uploaded successfully. File URL: " + fileUrl;
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (IOException ex) {
            message = "Error uploading file: " + ex.getMessage();
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
