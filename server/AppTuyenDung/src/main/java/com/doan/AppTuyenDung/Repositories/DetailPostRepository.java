package com.doan.AppTuyenDung.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.doan.AppTuyenDung.entity.DetailPost;

@Repository
public interface DetailPostRepository  extends JpaRepository<DetailPost,Integer>, JpaSpecificationExecutor<DetailPost>{
    
}
