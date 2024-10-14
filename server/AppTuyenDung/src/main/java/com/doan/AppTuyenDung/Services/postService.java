package com.doan.AppTuyenDung.Services;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.doan.AppTuyenDung.Repositories.CompanyRepository;
import com.doan.AppTuyenDung.Repositories.DetailPostRepository;
import com.doan.AppTuyenDung.Repositories.PostRepositoriesQuery;
import com.doan.AppTuyenDung.Repositories.PostRepository;
import com.doan.AppTuyenDung.Repositories.PostSpecification;
import com.doan.AppTuyenDung.Repositories.SearchRepository;
import com.doan.AppTuyenDung.Repositories.UserSpecification;
import com.doan.AppTuyenDung.Repositories.criteria.FilterData;
import com.doan.AppTuyenDung.entity.Company;
import com.doan.AppTuyenDung.entity.DetailPost;
import com.doan.AppTuyenDung.entity.Post;
import com.doan.AppTuyenDung.entity.User;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.doan.AppTuyenDung.DTO.DetailPostDTO;
import com.doan.AppTuyenDung.DTO.FilterCriteria;
import com.doan.AppTuyenDung.DTO.Response.AccountResponse;
import com.doan.AppTuyenDung.DTO.Response.CodeResponse;
import com.doan.AppTuyenDung.DTO.Response.PageResponse;
import com.doan.AppTuyenDung.DTO.Response.PostJobTypeCountDTO;
import com.doan.AppTuyenDung.DTO.Response.PostResponse;
import com.doan.AppTuyenDung.DTO.Response.postDetailResponse;
import com.doan.AppTuyenDung.DTO.InfoPostDetailDto;
import java.util.*;
import java.util.regex.Matcher;
import static com.doan.AppTuyenDung.utils.AppConst.SEARCH_SPEC_OPERATOR;
import static com.doan.AppTuyenDung.utils.AppConst.SORT_BY;

@Service
public class postService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostRepositoriesQuery postRepositoriesQuery;
    @Autowired 
    private CompanyRepository companyRepository;

    //amount post and get 
    public Page<PostJobTypeCountDTO> getPostJobTypeAndCountPost(Pageable pageable) {
        Page<Object[]> rawResults = postRepositoriesQuery.findPostJobTypeAndCountPost(pageable);

        List<PostJobTypeCountDTO> dtos = rawResults.stream()
            .map(result -> new PostJobTypeCountDTO(
                (String) result[0],          // categoryJobCode
                ((Number) result[1]).intValue(),
                (String) result[2],          // value
                (String) result[3],          // code
                (String) result[4]           // image
            ))
            .collect(Collectors.toList());
            int pageNumber = rawResults.getNumber(); // Current page number
            int pageSize = rawResults.getSize(); // Size of each page
            long totalElements = rawResults.getTotalElements(); // Total number of elements

            return new PageImpl<>(dtos, PageRequest.of(pageNumber, pageSize), totalElements);
    }

    // get Detail Post 
    public ResponseEntity<?> getPostDetailById(Integer id) {
        List<InfoPostDetailDto> postDetails = postRepositoriesQuery.findPostDetailById(id);

        InfoPostDetailDto postDetail = postDetails.stream().findFirst().orElse(null);

        if (postDetail != null) {
            return ResponseEntity.ok(postDetail); // Trả về DTO nếu tìm thấy
        } else {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                                 .body("Không tìm thấy bài viết"); // Trả về thông báo lỗi nếu không tìm thấy
        }
    }

    // Filter post and detail post
    public Page<DetailPostDTO> getFilteredDetailPosts(String categoryJobCode, String addressCode, String search,
                                                      List<String> experienceJobCodes, List<String> categoryWorktypeCodes,
                                                      List<String> salaryJobCodes, List<String> categoryJoblevelCodes,
                                                      Integer isHot, Pageable pageable) {
        if (search != null && !search.isEmpty()) 
        {
            search = "%" + search + "%";
        }
        
        return postRepositoriesQuery.findFilteredPosts(categoryJobCode, 
                                                            addressCode, 
                                                        search, 
                                                       experienceJobCodes,
                                                     categoryWorktypeCodes, 
                                                     salaryJobCodes, 
                                                     categoryJoblevelCodes, 
                                                     isHot, 
                                                     pageable);
    }


    public Map<String, Object> getStatisticalTypePost(int limit) {
        Map<String, Object> response = new HashMap<>();

        // Lấy danh sách các loại bài đăng và số lượng
        List<Map<String, Object>> statisticalTypePost = postRepository.findStatisticalTypePost(limit);

        List<Post> allPosts = postRepository.findAll();
            List<Post> filteredPosts = allPosts.stream()
                    .filter(post -> "PS1".equals(post.getStatusCode().getCode()))
                    .collect(Collectors.toList());
            
        long totalPosts = filteredPosts.size();

        response.put("errCode", 0);
        response.put("data", statisticalTypePost);
        response.put("totalPost", totalPosts);

        return response;
    }
    public Page<PostResponse> searchPosts(String name, String categoryJobCode, List<String> categoryWorkTypeCode, 
        String addressCode, List<String> experienceJobCode, List<String> categoryJobLevelCode, List<String> salaryJobCode,Integer isHot, Pageable pageable) {
        Specification<Post> spec = PostSpecification.filterPosts(name, categoryJobCode, categoryWorkTypeCode, 
                addressCode, experienceJobCode, categoryJobLevelCode, salaryJobCode, isHot);
        Page<PostResponse> pageRs = mapPostPageTPostResponsePage(postRepository.findAll(spec, pageable));
    return pageRs;
    }
    public Page<PostResponse> mapPostPageTPostResponsePage(Page<Post> postPage) {
        List<PostResponse> userResponses = postPage.getContent().stream()
            .map(post -> mapToPostResponse(post.getId()))
            .collect(Collectors.toList());

return new PageImpl<>(userResponses, postPage.getPageable(), postPage.getTotalElements());
}
    private PostResponse mapToPostResponse(Integer Id) {
    	Optional<Post> postOptional = postRepository.findById(Id);
    	Post p = postOptional.get();
    	PostResponse postData = new PostResponse();
    	postData.setUserId(p.getUser().getId());
    	Company c = companyRepository.findCompanyByUserId(p.getUser().getId());
    	if(c == null ) {
        	postData.setThumbnail("Chưa có thumbnail"); 
    	}
    	else {
        	postData.setThumbnail(c.getThumbnail() != null ? c.getThumbnail() : "Chưa có thumbnail"); 
    	}
    	postData.setCreatedAt(p.getCreatedAt());
    	postData.setId(p.getId());
    	postData.setIsHot(p.getIsHot());
    	postData.setStatusCode(p.getStatusCode().getCode());
    	postData.setTimeEnd(p.getTimeEnd());
    	postData.setTimePost(p.getTimePost());
    	postData.setUpdatedAt(p.getUpdatedAt());
		postDetailResponse postDetailResponse = new postDetailResponse();
    	if(p.getDetailPost()!=null) {
    		postDetailResponse.setId(p.getDetailPost().getId());
    		postDetailResponse.setName(p.getDetailPost().getName());
    		postDetailResponse.setDescriptionHTML(p.getDetailPost().getDescriptionHTML());
    		postDetailResponse.setDescriptionMarkdown(p.getDetailPost().getDescriptionMarkdown());
    		postDetailResponse.setAmount(p.getDetailPost().getAmount());
    		CodeResponse jobTypePostData = new CodeResponse();
            if(p.getDetailPost().getCategoryJobCode() != null) {
            	jobTypePostData.setCode(p.getDetailPost().getCategoryJobCode().getCode());
            	jobTypePostData.setValue(p.getDetailPost().getCategoryJobCode().getValue());
            	postDetailResponse.setJobTypePostData(jobTypePostData);
            }
            CodeResponse workTypePostData = new CodeResponse();
            if(p.getDetailPost().getCategoryWorktypeCode() != null) {
            	workTypePostData.setCode(p.getDetailPost().getCategoryWorktypeCode().getCode());
            	workTypePostData.setValue(p.getDetailPost().getCategoryWorktypeCode().getValue());
            	postDetailResponse.setWorkTypePostData(workTypePostData);
            }
            CodeResponse salaryTypePostData = new CodeResponse();
            if(p.getDetailPost().getSalaryJobCode()  != null) {
            	salaryTypePostData.setCode(p.getDetailPost().getSalaryJobCode().getCode());
            	salaryTypePostData.setValue(p.getDetailPost().getSalaryJobCode().getValue());
            	postDetailResponse.setSalaryTypePostData(salaryTypePostData);
            }
            CodeResponse jobLevelPostData = new CodeResponse();
            if(p.getDetailPost().getCategoryJoblevelCode()  != null) {
            	jobLevelPostData.setCode(p.getDetailPost().getCategoryJoblevelCode().getCode());
            	jobLevelPostData.setValue(p.getDetailPost().getCategoryJoblevelCode().getValue());
            	postDetailResponse.setJobLevelPostData(jobLevelPostData);
            }
            CodeResponse genderPostData = new CodeResponse();
            if(p.getDetailPost().getGenderPostCode() != null) {
            	genderPostData.setCode(p.getDetailPost().getGenderPostCode().getCode());
            	genderPostData.setValue(p.getDetailPost().getGenderPostCode().getValue());
            	postDetailResponse.setGenderPostData(genderPostData);
            }
            CodeResponse provincePostData = new CodeResponse();
            if(p.getDetailPost().getAddressCode() != null) {
            	provincePostData.setCode(p.getDetailPost().getAddressCode().getCode());
            	provincePostData.setValue(p.getDetailPost().getAddressCode().getValue());
            	postDetailResponse.setProvincePostData(provincePostData);
            }
            CodeResponse expTypePostData = new CodeResponse();
            if(p.getDetailPost().getExperienceJobCode() != null) {
            	expTypePostData.setCode(p.getDetailPost().getExperienceJobCode().getCode());
            	expTypePostData.setValue(p.getDetailPost().getExperienceJobCode().getValue());
            	postDetailResponse.setExpTypePostData(expTypePostData);
            }
            postData.setPostDetailData(postDetailResponse);
    	}
    	return postData;
    }

}


    

