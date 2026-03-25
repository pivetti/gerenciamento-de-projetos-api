package com.example.demo.repository;

import com.example.demo.entity.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
}
