package com.doan.AppTuyenDung.entity;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "packageposts")
public class PackagePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String value;
    private Double price;
    private Boolean isHot;
    private Boolean isActive;

    @OneToMany(mappedBy = "packagePost")
    private Set<OrderPackage> orderPackages;

    // Getters and Setters
}