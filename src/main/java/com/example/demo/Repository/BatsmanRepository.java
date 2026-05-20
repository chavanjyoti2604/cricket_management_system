package com.example.demo.Repository;

import com.example.demo.entity.Batsman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatsmanRepository extends JpaRepository<Batsman, Long> {
    boolean existsByBatsmanName(String batsmanName);
}