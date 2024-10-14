
package com.doan.AppTuyenDung.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doan.AppTuyenDung.entity.Cv;

public interface CvsRepository extends JpaRepository<Cv, Integer> {
	public Page<Cv> findByUserId(Integer id, Pageable pageable);
}
