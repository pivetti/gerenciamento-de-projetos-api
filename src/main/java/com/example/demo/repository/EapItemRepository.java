package com.example.demo.repository;

import com.example.demo.entity.EapItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EapItemRepository extends JpaRepository<EapItem, Long> {
}
