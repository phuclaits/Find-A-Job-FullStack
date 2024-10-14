package com.doan.AppTuyenDung.Services;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.doan.AppTuyenDung.DTO.CvDTO;
import com.doan.AppTuyenDung.DTO.Response.CvsResponse;
import com.doan.AppTuyenDung.Repositories.CvsRepository;
import com.doan.AppTuyenDung.Repositories.PostRepository;
import com.doan.AppTuyenDung.Repositories.UserRepository;
import com.doan.AppTuyenDung.entity.Cv;
import com.doan.AppTuyenDung.entity.DetailPost;
import com.doan.AppTuyenDung.entity.Post;
import com.doan.AppTuyenDung.entity.User;

@Service
public class CvService {
	@Autowired
	private CvsRepository cvRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	public Page<CvsResponse> getAllCvByUserId(int idUser, Pageable pageable) {
	    Page<Cv> cvsPage = cvRepository.findByUserId(idUser, pageable);
		 Page<CvsResponse> responsePage = cvsPage.map(this::convertCvsToCvsResponse);
		
	    return responsePage;
	}

	private CvsResponse convertCvsToCvsResponse(Cv cv) {
	    CvsResponse response = new CvsResponse();
	    response.setIdCv(cv.getId());
	    response.setFile(null);
	    //response.setFile(cv.getFile());
	    response.setIsChecked(cv.getIsChecked());
	    response.setStatus(cv.getStatus());
	    response.setInterviewTime(cv.getInterviewTime());
	    response.setDescription(cv.getDescription());
	    response.setCreatedAtCv(cv.getCreatedAt());
	    response.setUpdatedAtCv(cv.getUpdatedAt());
	    if (cv.getPost() != null) {
	        Post post = cv.getPost();
	        response.setIdPost(post.getId());
	        response.setTimeEnd(post.getTimeEnd());
	        response.setTimePost(post.getTimePost());
	        response.setIsHot(post.getIsHot());
	        response.setCreatedAtPost(post.getCreatedAt());
	        response.setUpdatedAtPost(post.getUpdatedAt());
	        if (post.getDetailPost() != null) {
	            DetailPost detailPost = post.getDetailPost();
	            response.setIdDetailPost(detailPost.getId());
	            response.setName(detailPost.getName());
	            response.setDescriptionHTML(detailPost.getDescriptionHTML());
	            response.setDescriptionMarkdown(detailPost.getDescriptionMarkdown());
	            response.setAmount(detailPost.getAmount());
	            response.setCodeJobType(detailPost.getCategoryJobCode().getCode());
	            response.setValueJobType(detailPost.getCategoryJobCode().getValue());
	            response.setCodeWorkType(detailPost.getCategoryWorktypeCode().getCode());
	            response.setValueWorkType(detailPost.getCategoryWorktypeCode().getValue());
	            response.setCodeSalaryType(detailPost.getSalaryJobCode().getCode());
	            response.setValueSalaryType(detailPost.getSalaryJobCode().getValue());
	            response.setCodeJobLevel(detailPost.getCategoryJoblevelCode().getCode());
	            response.setValueJobLevel(detailPost.getCategoryJoblevelCode().getValue());
	            response.setCodeGender(detailPost.getGenderPostCode().getCode());
	            response.setValueGender(detailPost.getGenderPostCode().getValue());
	            response.setCodeProvince(detailPost.getAddressCode().getCode());
	            response.setValueProvince(detailPost.getAddressCode().getValue());
	            response.setCodeExpType(detailPost.getExperienceJobCode().getCode());
	            response.setValueExpType(detailPost.getExperienceJobCode().getValue());
	        }
	    }
	    return response;
	}

	public Map<String, Object> handleCreateCv(CvDTO data) {
        Map<String, Object> response = new HashMap<>();
		
		User user = userRepository.findById(data.getUserId()).orElse(null);
		if(user == null) {
			response.put("errCode", -1);
            response.put("errMessage", "User not found!");
            return response;
		}
		Post post = postRepository.findById(data.getPostId()).orElse(null);
		if(post == null)
		{
			response.put("errCode", -1);
            response.put("errMessage", "Post not found!");
            return response;
		}

        if (data.getUserId() == null || data.getFile() == null || data.getPostId() == null) {
            response.put("errCode", 1);
            response.put("errMessage", "Missing required parameters!");
        } else {
            Cv cv = new Cv();
            cv.setUser(user);
            cv.setFile(data.getFile());
            cv.setPost(post);
            cv.setIsChecked(false); // Mặc định là chưa kiểm tra
            cv.setDescription(data.getDescription());

            Cv savedCv = cvRepository.save(cv);
            if (savedCv != null) {
                response.put("errCode", 0);
                response.put("errMessage", "Đã gửi CV thành công");
            } else {
                response.put("errCode", 2);
                response.put("errMessage", "Đã gửi CV thất bại");
            }
        }
        return response;
    }

}

