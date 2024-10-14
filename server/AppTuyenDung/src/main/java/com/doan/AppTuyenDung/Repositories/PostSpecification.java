package com.doan.AppTuyenDung.Repositories;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.doan.AppTuyenDung.entity.CodeExpType;
import com.doan.AppTuyenDung.entity.CodeJobLevel;
import com.doan.AppTuyenDung.entity.CodeJobType;
import com.doan.AppTuyenDung.entity.CodeProvince;
import com.doan.AppTuyenDung.entity.CodeSalaryType;
import com.doan.AppTuyenDung.entity.CodeWorkType;
import com.doan.AppTuyenDung.entity.DetailPost;
import com.doan.AppTuyenDung.entity.Post;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class PostSpecification {
    public static Specification<Post> filterPosts(String name, String categoryJobCode, List<String> categoryWorkTypeCode, 
            String addressCode, List<String> experienceJobCode, List<String> categoryJobLevelCode, List<String> salaryJobCode, Integer isHot) {
        return (root, query, criteriaBuilder) -> {
            Specification<Post> spec = Specification.where(null);
            
            if (name != null && !name.isEmpty()) {
                spec = spec.and((postRoot, postQuery, cb) -> {
                    Join<Post, DetailPost> detailPostJoin = postRoot.join("detailPost", JoinType.LEFT);
                    return cb.like(detailPostJoin.get("name"), "%" + name + "%");
                });
            }
            
            if (categoryJobCode != null && !categoryJobCode.isEmpty()) {
                spec = spec.and((postRoot, postQuery, cb) -> {
                    Join<Post, DetailPost> detailPostJoin = postRoot.join("detailPost", JoinType.LEFT);
                    Join<DetailPost, CodeJobType> categoryJobJoin = detailPostJoin.join("categoryJobCode", JoinType.LEFT);
                    return cb.equal(categoryJobJoin.get("code"), categoryJobCode);
                });
            }
            
            if (categoryWorkTypeCode != null && !categoryWorkTypeCode.isEmpty()) { 
                spec = spec.and((postRoot, postQuery, cb) -> {
                    Join<Post, DetailPost> detailPostJoin = postRoot.join("detailPost", JoinType.LEFT);
                    Join<DetailPost, CodeWorkType> workTypeJoin = detailPostJoin.join("categoryWorktypeCode", JoinType.LEFT);
                    return workTypeJoin.get("code").in(categoryWorkTypeCode);
                });
            }
            
            if (addressCode != null && !addressCode.isEmpty()) {
                spec = spec.and((postRoot, postQuery, cb) -> {
                    Join<Post, DetailPost> detailPostJoin = postRoot.join("detailPost", JoinType.LEFT);
                    Join<DetailPost, CodeProvince> provinceJoin = detailPostJoin.join("addressCode", JoinType.LEFT);
                    return cb.equal(provinceJoin.get("code"), addressCode);
                });
            }
            
            if (experienceJobCode != null && !experienceJobCode.isEmpty()) { 
                spec = spec.and((postRoot, postQuery, cb) -> {
                    Join<Post, DetailPost> detailPostJoin = postRoot.join("detailPost", JoinType.LEFT);
                    Join<DetailPost, CodeExpType> expTypeJobJoin = detailPostJoin.join("experienceJobCode", JoinType.LEFT);
                    return expTypeJobJoin.get("code").in(experienceJobCode);
                });
            }
            
            if (categoryJobLevelCode != null && !categoryJobLevelCode.isEmpty()) {
                spec = spec.and((postRoot, postQuery, cb) -> {
                    Join<Post, DetailPost> detailPostJoin = postRoot.join("detailPost", JoinType.LEFT);
                    Join<DetailPost, CodeJobLevel> joblevelJobJoin = detailPostJoin.join("categoryJoblevelCode", JoinType.LEFT);
                    return joblevelJobJoin.get("code").in(categoryJobLevelCode);
                });
            }
            
            if (salaryJobCode != null && !salaryJobCode.isEmpty()) {
                spec = spec.and((postRoot, postQuery, cb) -> {
                    Join<Post, DetailPost> detailPostJoin = postRoot.join("detailPost", JoinType.LEFT);
                    Join<DetailPost, CodeSalaryType> salaryJobJoin = detailPostJoin.join("salaryJobCode", JoinType.LEFT);
                    return salaryJobJoin.get("code").in(salaryJobCode);
                });
            }
            
            if (isHot != null && (isHot == 0 || isHot == 1)) {
                spec = spec.and((postRoot, postQuery, cb) -> {
                    return cb.equal(postRoot.get("isHot"), isHot);
                });
            }
            
            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}

