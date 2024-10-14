package com.doan.AppTuyenDung.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.ResponseDate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doan.AppTuyenDung.DTO.DetailPostDTO;
import com.doan.AppTuyenDung.DTO.Response.ApiResponse;
import com.doan.AppTuyenDung.DTO.Response.PostJobTypeCountDTO;
import com.doan.AppTuyenDung.DTO.Response.PostResponse;
import com.doan.AppTuyenDung.DTO.Response.ResponseData;
import com.doan.AppTuyenDung.Repositories.PostRepository;
import com.doan.AppTuyenDung.Repositories.SearchRepository;
import com.doan.AppTuyenDung.Repositories.criteria.FilterData;
import com.doan.AppTuyenDung.Services.postService;
import com.doan.AppTuyenDung.entity.Post;


@RestController
@RequestMapping()
public class postController {
    @Autowired
    private postService postService;
    @Autowired ModelMapper modelMapper;

    @Autowired
    private SearchRepository searchRepository;
    @Autowired
    private PostRepository postRepository;

    


    @GetMapping("/public/get-filter-post")
    public ResponseEntity<Page<DetailPostDTO>> getFilteredDetailPosts(@RequestParam(required = false) String categoryJobCode,
                                                              @RequestParam(required = false) String addressCode,
                                                              @RequestParam(required = false) String search,
                                                              @RequestParam(required = false) List<String> experienceJobCodes,
                                                              @RequestParam(required = false) List<String> categoryWorktypeCodes,
                                                              @RequestParam(required = false) List<String> salaryJobCodes,
                                                              @RequestParam(required = false) List<String> categoryJoblevelCodes,
                                                              @RequestParam(required = false) Integer isHot,
                                                              @RequestParam(defaultValue = "0") int offset,
                                                              @RequestParam(defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<DetailPostDTO> detailPosts = postService.getFilteredDetailPosts(categoryJobCode, addressCode, (search !=null ? search.toUpperCase() : null),
                                                                            experienceJobCodes, categoryWorktypeCodes,
                                                                            salaryJobCodes, categoryJoblevelCodes,
                                                                            isHot, pageable);
        return ResponseEntity.ok(detailPosts);
    }
    @GetMapping("/public/get-list-job-count-post")
    public Page<PostJobTypeCountDTO> getListJobTypeAndCountPost(@RequestParam(defaultValue = "0") int offset,
                                                                @RequestParam(defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return  postService.getPostJobTypeAndCountPost(pageable);
    }
    @GetMapping("/public/get-detail-post-by-id")
    public ResponseEntity<?> getPostDetailById(@RequestParam Integer id) {
        return postService.getPostDetailById(id);
    }
    
    @GetMapping("/public/get-statistical-post")
    public ResponseEntity<Map<String, Object>> getStatisticalTypePost(
            @RequestParam int limit) {

        Map<String, Object> response = postService.getStatisticalTypePost(limit);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/public/get-search-post")
    public ApiResponse<Page<PostResponse>> getSearchDetailPosts(@RequestParam(required = false) String name,
    														  @RequestParam(required = false) String categoryJobCode,
                                                              @RequestParam(required = false) String addressCode,
                                                              @RequestParam(required = false) List<String> experienceJobCode,
                                                              @RequestParam(required = false) List<String> categoryWorktypeCode,
                                                              @RequestParam(required = false) List<String> salaryJobCode,
                                                              @RequestParam(required = false) List<String> categoryJoblevelCode,
                                                              @RequestParam(required = false) Integer isHot,
                                                              @RequestParam(defaultValue = "0") int offset,
                                                              @RequestParam(defaultValue = "10") int limit) {
    	ApiResponse<Page<PostResponse>> apiRP = new ApiResponse<Page<PostResponse>>();
    	try {
    		Pageable pageable = PageRequest.of(offset, limit);
            Page<PostResponse> postRP = postService.searchPosts(name,categoryJobCode, categoryWorktypeCode,
    											        		addressCode, experienceJobCode,
    											        		categoryJoblevelCode, salaryJobCode,
                                                                isHot, pageable);
            apiRP.setResult(postRP);
    	}
    	catch (Exception e) {
    		apiRP.setCode(404);
    		apiRP.setMessage(e.getMessage());
		}
        
        return apiRP;
    }
}
