package com.doan.AppTuyenDung.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doan.AppTuyenDung.entity.PackageCv;

@Repository
public interface PackageCvRepository extends JpaRepository<PackageCv, Integer> {
}