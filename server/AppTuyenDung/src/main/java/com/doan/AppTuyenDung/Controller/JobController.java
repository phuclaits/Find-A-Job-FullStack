package com.doan.AppTuyenDung.Controller;

import java.io.IOException;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.doan.AppTuyenDung.DTO.CreateCompanyRequest;
import com.doan.AppTuyenDung.DTO.CreateCompanyResponse;


// import com.doan.AppTuyenDung.Repositories.Job__ModelRepository;
// import com.doan.AppTuyenDung.Services.JobService;
// // import com.doan.AppTuyenDung.entity.Job_Model;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/job")
// @RequiredArgsConstructor
public class JobController {
    // @Autowired
    // public JobService jobService;
    
    // @Autowired
    // public Job__ModelRepository jobRepository;

    public class CompanyController {

    // @Autowired
    // private JobService jobService;

    // @PostMapping("/create")
    // public ResponseEntity<CreateCompanyResponse> createCompany(
    //         @RequestParam("name") String name,
    //         @RequestParam("thumbnail") MultipartFile thumbnail,
    //         @RequestParam("coverimage") MultipartFile coverimage,
    //         @RequestParam("descriptionHTML") String descriptionHTML,
    //         @RequestParam("descriptionMarkdown") String descriptionMarkdown,
    //         @RequestParam("website") String website,
    //         @RequestParam("address") String address,
    //         @RequestParam("phonenumber") String phonenumber,
    //         @RequestParam("amountEmployer") Integer amountEmployer,
    //         @RequestParam("taxnumber") String taxnumber,
    //         @RequestParam("file") String file,
    //         @RequestParam("userId") Integer userId) {

    //     CreateCompanyRequest request = new CreateCompanyRequest();
    //     request.setName(name);
    //     request.setThumbnail(thumbnail);
    //     request.setCoverimage(coverimage);
    //     request.setDescriptionHTML(descriptionHTML);
    //     request.setDescriptionMarkdown(descriptionMarkdown);
    //     request.setWebsite(website);
    //     request.setAddress(address);
    //     request.setPhonenumber(phonenumber);
    //     request.setAmountEmployer(amountEmployer);
    //     request.setTaxnumber(taxnumber);
    //     request.setFile(file);
    //     request.setUserId(userId);

    //     CreateCompanyResponse response = jobService.createCompany(request);

    //     if (response.getErrCode() == 0) {
    //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    //     }
    // }
}
    // @GetMapping("")
	// public ResponseEntity<List<Job_Model>> getAllJobModel(){

	// 	System.out.println("Get all job in controller");
	// 		List<Job_Model> jobs = jobService.getAllJobModel();
	// 		return new ResponseEntity<List<Job_Model>>(jobs, HttpStatus.OK);
	// }

    // @GetMapping("/getJob/{jobId}")
    // public ResponseEntity<Job_Model> getJobModelbyId(@PathVariable("jobId") Long jobId) {
    //     Job_Model job = jobService.getJob_ModelById(jobId);
    //     return ResponseEntity.ok(job);
    // }

    // @PostMapping("/add-new-job")
    // public ResponseEntity<?> addNewJobSv(@RequestBody Job_Model jobs ){
    //     try {
    //         System.out.println("Controller active");
            
	// 		return new ResponseEntity<>(jobService.addNewJob(jobs), HttpStatus.OK);
	// 	} catch (Exception e) {
	// 		System.out.println("Exception: " + e.getMessage());
	// 		return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
	// 	}
    // }

    
}
